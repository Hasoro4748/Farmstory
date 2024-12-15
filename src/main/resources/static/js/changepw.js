// 비밀번호 유효성 검사
const newPassword = document.getElementById('newPassword'); // 비밀번호 입력 필드들
const confirmPassword = document.getElementById('confirmPassword'); // 비밀번호 입력 필드들
let isPassOk = false; // 비밀번호 검증 결과를 저장하는 플래그
console.log(confirmPassword)

// 비밀번호 형식 확인 정규식 (8자 이상, 영문, 숫자, 특수문자 포함)

const passwordPattern = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;

// 비밀번호 확인 필드에 대한 이벤트 리스너 추가
confirmPassword.addEventListener('focusout', () => {
    // 비밀번호와 비밀번호 확인 필드가 일치하는지 확인
    if (newPassword.value === confirmPassword.value) {
        // 비밀번호 형식 확인
        if (!passwordPattern.test(confirmPassword.value)) {

            showResultInvalid(resultPass, '비밀번호 형식에 맞지 않습니다.');

            isPassOk = false;
        } else {
            showResultValid(resultPass, '사용 가능한 비밀번호입니다.');
            isPassOk = true;
        }
    } else {
        showResultInvalid(resultPass, '비밀번호가 일치하지 않습니다.');
        isPassOk = false;
    }
});

// 폼 제출 시 검증

document.getElementById('passwordResetForm').onsubmit = function (e) {
    e.preventDefault(); // 기본 폼 제출을 방지

    if (!isPassOk) {
        alert('비밀번호를 다시 확인해 주세요.');
        return false; // 검증 실패 시 폼 제출을 막음
    }

    // 서버로 비밀번호 변경 요청 보내기
    fetch('/api/user/changepass', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            uid: document.getElementById('uid').innerText,  // 사용자 아이디
            newpass: newPassword.value,  // 새로운 비밀번호

        })
    })
        .then(response => {
            if(response.ok === false)
            {
                throw new Error("비밀번호 수정 에러")
            }

            alert('비밀번호가 성공적으로 변경되었습니다.');
                // 비밀번호 변경 성공 시 로그인 페이지로 리다이렉트
                window.location.href = '/category/user/login';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('비밀번호 변경 중 오류가 발생했습니다.');
        });

    return false; // 서버로 요청을 보내고 나서 폼 제출을 막음
};
    document.querySelector(".btnCancel").addEventListener("click", function () {
    window.history.back()      });
// 로그인 페이지로 리다이렉트

