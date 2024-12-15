// 회원 상세 정보 모달처리
// 모달을 열 때 UID 값을 설정하는 기능
document.querySelectorAll('.openModalBtn').forEach(button => {
    button.addEventListener('click', function() {
        // 클릭한 버튼의 부모 tr 요소를 찾음
        const userRow = this.closest('tr');

        // 2번째 td 요소에서 UID 값을 가져옴
        const uid = userRow.querySelector('.user-uid').textContent.trim();

        console.log('uidddddddddddddddddd' + uid);
        // UID를 모달에 표시
        document.getElementById('modalUserId').value = uid;

        fetch('/admin/findUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // JSON 데이터 형식으로 설정
            },
            body: JSON.stringify({ uid: uid })
        })
            .then(resp => resp.json())
            .then(data => {
                console.log(data);
                if (data) { // data가 유효하면 성공으로 간주
                   // alert('성공');

                    if (data.role === 'LEAVE') {
                        // 해당 td 요소 선택
                        const quitTd = document.querySelector('.form_group.quit');
                        const roleTd = document.querySelector('#modalUserRole').closest('td');

                        // 버튼 제거
                        const quitButton = quitTd.querySelector('#btn_quit');
                        const blackButton = quitTd.querySelector('#btn_black');
                        const adminButton = quitTd.querySelector('#btn_admin');

                        if (quitButton || blackButton || adminButton) {
                            quitTd.removeChild(quitButton); // 탈퇴하기 버튼 제거
                            quitTd.removeChild(blackButton); // 블랙리스트 버튼 제거
                            roleTd.removeChild(adminButton); // 관리자승급 버튼 제거
                        }

                        // 존재한다면 생성 방지
                        let leaveInput = quitTd.querySelector('#modalUserLeaveDate');
                        let leaveSpan = quitTd.querySelector('#modalUserLeaveDatespan');

                        // input 요소 생성 (존재하지 않을 경우에만)
                        if (!leaveInput) {
                            const input = document.createElement('input');
                            input.type = 'text';
                            input.name = 'regdate';
                            input.id = 'modalUserLeaveDate';
                            input.className = 'inputRegDate';
                            input.readOnly = true; // readonly 속성 추가
                            input.value = data.leaveDate.split('T')[0];

                            // 요소를 quitTd에 추가
                            quitTd.prepend(input); // input을 먼저 추가
                        }

                        // span 요소 생성 (존재하지 않을 경우에만)
                        if (!leaveSpan) {
                            const span = document.createElement('span');
                            span.innerText = '탈퇴 일자';
                            span.id = 'modalUserLeaveDatespan';
                            quitTd.prepend(span); // span을 추가
                        }

                        return;
                    }

                    // 버튼 추가
                    const quitTd = document.querySelector('.form_group.quit');
                    const quitButton = document.querySelector('#btn_quit');
                    const blackButton = document.querySelector('#btn_black');

                    // 탈퇴버튼 추가
                    if (!quitButton) {
                        const quitButton = document.createElement('button');
                        quitButton.type = 'button';
                        quitButton.className = 'btn_quit btn btn-primary'; // Bootstrap 스타일 추가
                        quitButton.id = 'btn_quit';
                        quitButton.textContent = '탈퇴하기'; // 텍스트 설정
                        quitButton.style.width = '70px'
                        quitTd.appendChild(quitButton);

                        quitButton.addEventListener('click', function() {
                            const uid = document.getElementById('modalUserId').value;
                            modifyRole(uid, 'LEAVE'); // QUIT 역할로 변경
                        });
                    }

                    // 블랙리스트 버튼 추가
                    if (!blackButton) {
                        const blackButton = document.createElement('button');
                        blackButton.type = 'button';
                        blackButton.className = 'btn_black btn btn-danger'; // Bootstrap 스타일 추가
                        blackButton.id = 'btn_black';
                        blackButton.textContent = '블랙리스트'; // 텍스트 설정
                        blackButton.style.width = '80px'
                        quitTd.appendChild(blackButton);

                        // 클릭 이벤트 리스너 추가
                        blackButton.addEventListener('click', function() {
                            const uid = document.getElementById('modalUserId').value;
                            modifyRole(uid, 'BLACK'); // BLACK 역할로 변경
                        });
                    }

                    // 관리자 버튼 추가
                    const roleTd = document.querySelector('#modalUserRole').closest('td');
                    const aadminButton = document.querySelector('#btn_admin');

                    // 관리자 버튼이 없다면 추가
                    if (!aadminButton) {
                        adminButton = document.createElement('button');
                        adminButton.type = 'button';
                        adminButton.className = 'btn btn-dark btn_Admin'; // Bootstrap 스타일 추가
                        adminButton.id = 'btn_admin';
                        adminButton.textContent = '관리자승급'; // 텍스트 설정
                        roleTd.appendChild(adminButton);

                        // 클릭 이벤트 리스너 추가
                        adminButton.addEventListener('click', function() {
                            modifyRole(uid, 'ADMIN'); // ADMIN 역할로 변경
                        });
                    }

                    // 버튼들을 보기 좋게 정렬하기 위한 CSS 클래스 추가
                    const buttons = quitTd.querySelectorAll('button');
                    buttons.forEach(button => {
                        button.classList.add('btn', 'btn-primary', 'mx-1'); // Bootstrap 버튼 스타일 추가
                    });

                    // 추가적으로 모달에 사용자 정보를 표시할 수 있습니다.
                    document.getElementById('modalUserName').value = data.name;
                    document.getElementById('modalUserRole').value = data.role;
                    document.getElementById('modalUserNick').value  = data.nick;
                    document.getElementById('modalUserHp').value  = data.hp;
                    document.getElementById('modalUserEmail').value  = data.email;
                    document.getElementById('modalUserZip').value  = data.zip;
                    document.getElementById('modalUserAddr1').value  = data.addr1;
                    document.getElementById('modalUserAddr2').value  = data.addr2;
                    document.getElementById('modalUserRegDate').value = data.regDate.split('T')[0];
                } else {
                    alert('실패');
                }
            })
            .catch(err => {
                console.error(err); // 에러를 콘솔에 출력
            });
    });
});

// 모달이 닫힐 때 입력 필드 값을 초기화
document.getElementById('confirmationModal').addEventListener('hidden.bs.modal', function () {
    const leaveInput = document.getElementById('modalUserLeaveDate');
    const leaveSpan = document.getElementById('modalUserLeaveDatespan');

    if (leaveInput) {
        leaveInput.parentNode.removeChild(leaveInput); // input 요소 제거
    }

    if (leaveSpan) {
        leaveSpan.parentNode.removeChild(leaveSpan); // span 요소 제거
    }
    document.getElementById('modalUserName').value = '';
    document.getElementById('modalUserRole').value = '';
    document.getElementById('modalUserNick').value = '';
    document.getElementById('modalUserHp').value = '';
    document.getElementById('modalUserEmail').value = '';
    document.getElementById('modalUserZip').value = '';
    document.getElementById('modalUserAddr1').value = '';
    document.getElementById('modalUserAddr2').value = '';
    document.getElementById('modalUserRegDate').value = '';

});

// 역할 변경 메서드
function modifyRole(uid, role) {
    fetch('/admin/modifyRole', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ uid: uid, role: role })
    })
        .then(resp => resp.json())
        .then(data => {
            console.log(data);
            if (data) {
                alert('성공');
                document.getElementById('modalUserRole').value = data.role;
                // 모달을 닫고 다시 열지 않도록 주석 처리
                modal.hide();
                modal.show();
            } else {
                alert('실패');
            }
        })
        .catch(err => {
            console.error(err);
        });
}


document.querySelector('.btn_Admin').addEventListener('click', function() {
    const uid = document.getElementById('modalUserId').value;
    modifyRole(uid, 'ADMIN'); // ADMIN 역할로 변경
});
