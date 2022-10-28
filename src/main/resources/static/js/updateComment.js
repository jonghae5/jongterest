const main = {
init : function() {
    const _this = this;
     // 댓글 수정
    document.querySelectorAll('#btn-comment-update')
    .forEach(function (item) {
        item.addEventListener('click', function () {
         // 버튼 클릭 이벤트 발생시
         const form = this.closest('form');
          // btn의 가장 가까운 조상의 Element(form)를 반환 (closest)
          _this.commentUpdate(form);
           // 해당 form으로 업데이트 수행
       });
   });
},

commentUpdate : function (form) {
   const data = {
       userId: form.querySelector('#id').value,
       articleId: form.querySelector('#article_id').value,
       commentId: form.querySelector('#comment_id').value,
       content: form.querySelector('#comment-content').value,
               }
       if (!data.content || data.content.trim() === "") {
           alert("공백 또는 입력하지 않은 부분이 있습니다.");
           return false;
           }
           const con_check = confirm("수정하시겠습니까?");
       if (con_check === true) {
       $.ajax({
       type: 'POST',
       url: '/comments/update/' + data.articleId + "/" + data.commentId,
       dataType: 'JSON',
       contentType: 'application/json; charset=utf-8',
       data: JSON.stringify(data)
       }).done(function () {
       window.location.reload();
       }).fail(function (error) {
           alert(JSON.stringify(error));
       });
       }
    }
}

main.init();