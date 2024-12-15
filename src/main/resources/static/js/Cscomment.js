
window.onload = function () {


    const btnComplete = document.getElementById('btnComplete');
    const commentForm = document.commentForm;
    const commentList = document.getElementsByClassName('commentList')[0];
    let originalText = '';

    // 이벤트 위임 - commentList에 이벤트 리스너 추가
    commentList.addEventListener('click', function(e) {
        const article = e.target.closest('article');
        console.log(article);
        const textarea = article.querySelector('textarea');
        const commentRemove = article.querySelector('.commentRemove');
        const commentModify=article.querySelector('.commentModify');
        const commentCancel = article.querySelector('.commentCancel');
        const commentCompleted= article.querySelector('.commentCompleted');


        if(e.target.classList.contains('commentModify')){
            e.preventDefault();

            originalText = textarea.value;
            textareaEditMode(true);

            console.log("originalText : "+originalText);
        }

        if(e.target.classList.contains('commentCompleted')){
            e.preventDefault();
            const no= commentCompleted.dataset.no;
            const comment = textarea.value;

            const formData= new FormData();
            formData.append("no",no);
            formData.append("comment",comment);

            fetch('/comment/modify',{
                method: 'POST',
                body: formData
            }).then(resp=>resp.json())
                .then(data => {
                    console.log(data);

                    if(data!=null){
                        alert('댓글이 수정되었습니다.')
                        textareaEditMode(false);
                    }
                }).catch(err=>{
                console.log(err);
            })

        }

        if(e.target.classList.contains('commentCancel')){
            e.preventDefault();
            let cancel = confirm('취소하시겠습니까?')

            if(cancel){
                textarea.value=originalText;
                textareaEditMode(false);
            }else{

            }

        }


        if(e.target.classList.contains('commentRemove')){
            e.preventDefault();
            if(!confirm('정말 삭제하시겠습니까?')){
                return;
            }

            const no=e.target.dataset.no;
            const pg = e.target.dataset.pg;
            const writer = e.target.dataset.writer;
            const cateNo = e.target.dataset.cateNo;
            console.log("no - "+no+", pg -"+pg+" ,writer - "+writer+"cateNo - "+cateNo);

            fetch('/comment/delete?no='+no+'&pg='+pg+'&writer='+writer)
                .then(resp =>resp.json())
                .then(data => {
                    console.log(data);
                    if(data >0 ){
                        alert('댓글이 삭제되었습니다.')
                        article.remove();
                    }else{
                        alert('댓글 삭제가 실패되었습니다.')
                    }
                }).catch(err=>{
                console.log(err);
            })


        }


        function textareaEditMode(edit){
            if(edit){
                textarea.readOnly=false;
                textarea.style.background='white';
                textarea.style.border='1px solid #555';
                textarea.focus();
                commentCompleted.hidden=false;
                commentCancel.hidden=false;
                commentModify.hidden=true;

            }else{
                textarea.readOnly=true;
                textarea.style.background='transparent';
                textarea.style.border='none';
                commentModify.hidden=false;
                commentCompleted.hidden=true;
                commentCancel.hidden=true;

            }
        }


    });// addEventListener  끝


    btnComplete.onclick = async function (e) {
        e.preventDefault();

        const jsonData = {
            "writer": commentForm.writer.value,
            "csParent": commentForm.csParent.value,
            "content": commentForm.content.value,
            "user" : {
                "uid": commentForm.writer.value,
                "nick": commentForm.nick.value
            }
        }
        const type='cs';
        const data = await fetchPost('/comment/write', jsonData)

        if(data.no){
            alert('댓글이 등록되었습니다.')
            console.log(data.user.uid);
            console.log(data.no);
            console.log(commentForm.commentSize.value);

                location.href = "/article/504/" + data.csParent+ "?content=csview&pg=" + commentForm.pg.value;

        }else{
            alert('댓글 등록 실패');
        }


    }



}