
window.onload = function () {

    // 입력필드
    const pName = document.getElementById('input_pName');
    const price = document.getElementById('input_price');
    const point = document.getElementById('input_point');
    const fileInputs = [
        document.querySelector('input[name="files1"]'),
        document.querySelector('input[name="files2"]'),
        document.querySelector('input[name="files3"]')
    ];
    const stock = document.getElementById('input_stock');


    // 제품 타입 불러오기
    const category = document.querySelector('.category');

    fetch('/admin/prodcate', {
        method: 'GET'

    })
        .then(resp => resp.json())
        .then(data => {
            console.log(data);

            for (const prodcate of data) {
                const prodcate_opt = document.createElement('option');
                prodcate_opt.setAttribute('value', prodcate.prodCateNo);
                prodcate_opt.innerText = prodcate.prodCateName;
                category.appendChild(prodcate_opt);
            }

        })
        .catch(err => {
            console.log(err);
        })

    // 할인율 동적 생성
    const discount = document.querySelector('.discount');

    for(let i=5; i<=50; i+=5) {
        const discount_opt = document.createElement('option');
        discount_opt.setAttribute('value', i);
        discount_opt.setAttribute('name', 'discount');
        discount_opt.innerText = i+'%';
        discount.appendChild(discount_opt);
    }

    // Form 제출을 처리할때 모든 파일 첨부해야 submit 하는 메서드
    document.getElementById('uploadForm').addEventListener('submit', function(event) {


        let pNameSelected = true;
        let priceSelected = true;
        let allFilesSelected = true;
        let stockSelected = true;

        // 공백 검사
        if(pName.value.trim() === ''){
            pNameSelected = false;
        }

        if(price.value.trim() === ''){
            priceSelected = false;
        }

        if(point.value.trim() === ''){
            pointSelected = false;
        }

        fileInputs.forEach(input => {
            if (input.files.length === 0) {
                allFilesSelected = false;
            }
        });

        if(stock.value.trim() === ''){
            stockSelected = false;
        }

        // 경고 메서드
        if (!pNameSelected) {
            event.preventDefault(); // 폼 제출 중단
            alert('상품명을 입력해야 합니다.'); // 경고 메시지
            return false;
        }

        if (!priceSelected) {
            event.preventDefault(); // 폼 제출 중단
            alert('가격을 입력해야 합니다.'); // 경고 메시지
            return false;
        }

        if (!stockSelected) {
            event.preventDefault(); // 폼 제출 중단
            alert('재고를 입력해야 합니다.'); // 경고 메시지
            return false;
        }

        if (!allFilesSelected) {
            event.preventDefault(); // 폼 제출 중단
            alert('모든 파일을 선택해야 합니다.'); // 경고 메시지
            return false;
        }
        return true;
    });
}

function updateFileName(input, labelId) {
    const label = document.getElementById(labelId);

    const fileName = input.files[0] ? input.files[0].name : 'No file chosen';
    label.textContent = fileName;
}

function updatePoint() {

    const priceField = document.getElementById('input_price');
    const pointField = document.getElementById('input_point');

    const priceValue = parseFloat(priceField.value) || 0; // 입력된 값, 없으면 0
    const pointValue = priceValue * 0.01; // 가격의 1%

    pointField.value = pointValue.toFixed(0); // 소수점 2자리로 설정
}

