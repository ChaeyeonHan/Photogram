// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault(); // 폼태그 액션을 막아준다(더이상 진행되지 않도록)

    let data = $("#profileUpdate").serialize();

    console.log(data);

    $.ajax({
        type:"put",
        url:`/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res=>{  // HttpStatus상태코드 200번대
        console.log("update 성공", res);
        location.href=`/user/${userId}`;
    }).fail(error=>{  // HttpStatus상태코드가 200번대가 아닐 때
        alert(JSON.stringify(error.responseJSON.data));
        console.log("실패", error.responseJSON.data.name);
    });
}