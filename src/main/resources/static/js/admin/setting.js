const links = document.getElementsByClassName('link');

for (let i = 0; i < links.length; i++) {
    links[i].addEventListener('click', function(event) {
        event.preventDefault(); // 기본 링크 동작 방지
        this.classList.toggle('underline'); // underline 클래스 토글
    });
}