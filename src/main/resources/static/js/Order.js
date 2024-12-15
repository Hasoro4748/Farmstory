window.onload = function () {
    document.getElementById('set_user').addEventListener('click', function(event) {

        event.preventDefault(); // 기본 동작 방지

        // 입력 필드에 값 설정
        document.getElementById('username').value = user.name;
        document.getElementById('hp').value = user.hp;
        document.getElementById('senderhp').value = user.hp;
        document.getElementById('sendername').value = user.name; // 받는분
        document.getElementById('recZip').value = user.zip; // 우편번호
        document.getElementById('recAddr1').value = user.addr1; // 기본주소
        document.getElementById('recAddr2').value = user.addr2; // 상세주소
    });

    // 포인트
    const availablePoints = parseInt(document.getElementById('available-points').innerText);
    const pointInput = document.querySelector('.point');
    const errorDiv = document.getElementById('point-error');
    const pointButton = document.querySelector('.point_btn');

    pointInput.addEventListener('input', function() {
        const enteredPoints = parseInt(pointInput.value);

        if (enteredPoints > availablePoints) {
            errorDiv.style.display = 'block'; // 경고 메시지 표시
        } else {
            errorDiv.style.display = 'none'; // 경고 메시지 숨기기
        }
    });


        // 버튼 클릭 이벤트 처리
        pointButton.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 동작 방지
            const pointButton = document.querySelector('.point_btn');
            const pointInput = document.querySelector('.point');
            const pointDisplay = document.querySelector('.pointdisplay'); // 여기에서 정확한 요소가 선택되는지 확인
            const hiddenInput = document.getElementById('usedpoint');
            const enteredPoints = parseInt(pointInput.value);

            // 입력값이 유효한 경우
            if (enteredPoints <= availablePoints && enteredPoints > 0) {
                pointInput.setAttribute('readonly', true); // readonly로 설정
                pointButton.textContent = '사용됨'; // 버튼 텍스트 변경

                // 입력한 포인트를 td에 표시 (textContent 사용)
                if (pointDisplay) {
                    pointDisplay.textContent = enteredPoints; // td의 텍스트 업데이트
                } else {
                    console.error("포인트를 표시할 수 없습니다. point-display 요소를 찾을 수 없습니다.");
                }

                // hidden input에 값 세팅 (value 속성 직접 변경)
                if (hiddenInput) {
                    hiddenInput.value = enteredPoints; // hidden input의 value 값 설정
                }
            } else {
                alert('사용 가능한 포인트를 입력해 주세요.');
            }
        });
}


