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
                alert('탈퇴가 완료되었습니다');
                window.location.href = '/user/logout';
            } else {
                alert('실패');
            }
        })
        .catch(err => {
            console.error(err);
        });
}


document.querySelector('.btn_quit').addEventListener('click', function() {
    const uid = document.getElementById('modalUserId').value;
    modifyRole(uid, 'LEAVE'); // ADMIN 역할로 변경
});

document.getElementById("modifyPassBtn").addEventListener("click", function () {
    const passInput = document.querySelector("input[name='pass']");
    const passConfirmInput = document.querySelector("input[name='pass2']");
    const resultPass = document.getElementById("resultPass");
    const resultPass2 = document.getElementById("resultPass2");
    const passwordMessage = document.getElementById("passwordMessage"); // 비밀번호 변경 메시지

    const newPassword = passInput.value;
    const confirmPassword = passConfirmInput.value;

    // 비밀번호 유효성 검사 정규식 (5~16자, 숫자/문자/특수문자 포함)
    const rePass = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;

    // 비밀번호 유효성 검사
    if (!newPassword.match(rePass)) {
        resultPass2.textContent = "비밀번호 형식에 맞지 않습니다.";
        resultPass2.style.color = "red";
        return;
    } else {

    }

    // 비밀번호 확인 일치 여부 검사
    if (newPassword !== confirmPassword) {
        resultPass2.textContent = "비밀번호가 일치하지 않습니다.";
        resultPass2.style.color = "red";
        return;
    } else {
        resultPass2.textContent = "사용 가능한 비밀번호 입니다.";
        resultPass2.style.color = "green";
    }

    // 비밀번호 변경 API 호출
    fetch('/api/myinfo/modifypass', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ newpass: newPassword })
    })
        .then(response => response.json())
        .then(data => {
            if (data.result) {
                passwordMessage.textContent = "비밀번호가 성공적으로 변경되었습니다.";
                passwordMessage.style.color = "green";
            } else {
                passwordMessage.textContent = "비밀번호 변경에 실패했습니다.";
                passwordMessage.style.color = "red";
            }
        })
        .catch(error => {
            console.error("비밀번호 변경 중 오류 발생:", error);
            passwordMessage.textContent = "오류가 발생했습니다. 다시 시도해 주세요.";
            passwordMessage.style.color = "red";
        });
});





// 별명 중복 확인 처리
document.getElementById("btnCheckNick").addEventListener("click", function () {
    const nick = document.querySelector("input[name='nick']").value;

    fetch(`/api/user/check-nick?nick=${encodeURIComponent(nick)}`)//??????
        .then(response => response.json())
        .then(data => {
            const resultNick = document.getElementById("resultNick");
            if (data.result) {
                resultNick.textContent = "이미 사용 중인 별명입니다."; //메시지로떠라 ㅡㅡ
                resultNick.style.color = "red";
            } else {
                resultNick.textContent = "사용 가능한 별명입니다.";
                resultNick.style.color = "green";
            }
        })
        .catch(error => {
            const resultNick = document.getElementById("resultNick");
            resultNick.textContent = "별명 확인 중 오류가 발생했습니다."; // 에러 메시지 표시
            resultNick.style.color = "red";
            console.error(error);
        });
});

// 회원 정보 수정
document.querySelector(".btn_submit").addEventListener("click", function (e) {
    e.preventDefault();  // 기본 폼 제출 막기

    const data = {
        pass: document.querySelector("input[name='pass']").value,
        nick: document.querySelector("input[name='nick']").value,
        zip: document.querySelector("input[name='zip']").value,
        addr1: document.querySelector("input[name='addr1']").value,
        addr2: document.querySelector("input[name='addr2']").value,
        email: document.querySelector("input[name='email']").value,
        hp: document.querySelector("input[name='hp']").value,
    };

    // AJAX 요청으로 회원 정보 수정 API 호출
    fetch("/api/user/update", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            const resultMessage = document.getElementById("resultMessage");

            if (response.ok) {
                resultMessage.textContent = "회원 정보가 성공적으로 수정되었습니다."; // 성공 메시지 표시
                resultMessage.style.color = "green";
                alert("회원수정이 성공했습니다.")
                window.location.href = "/";  // 수정 완료 후 리다이렉트
            } else {
                resultMessage.textContent = "회원 정보 수정에 실패했습니다."; // 실패 메시지 표시
                resultMessage.style.color = "red";
            }
        })
        .catch(error => {
            const resultMessage = document.getElementById("resultMessage");
            resultMessage.textContent = "오류가 발생했습니다: " + error.message; // 에러 메시지 표시
            resultMessage.style.color = "red";
            console.error(error);
        });
});