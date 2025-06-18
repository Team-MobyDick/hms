function goMain(url) {

  if(url == '/'){
    location.reload(true);
    return;
  }

  $.ajax({
    type: 'POST',
    url: url,
    async:false,
    data: "",
    contentType:"application/x-www-form-urlencoded; charset=UTF-8",
    success: function(data) {
      $('#Container').html(data);

      if(isMenuHide) menuOff();
    },
    error: function(request, status, error) {
      alert(error);
    }
  });

}