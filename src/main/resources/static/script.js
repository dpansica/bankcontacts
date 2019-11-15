$(document).ready(function () {

    callEndpoint("http://127.0.0.1:8080/contacts-app/contacts", {}, refreshContactList);

    var $body = $('body');

    $body.on('click', 'div.master_list div.list-group button', function () {
        var $button = $(this),
            article_option = $button.attr('data-option'),
            article_selector = 'article.' + article_option,
            $master_detail = $button.closest('.master_detail'),
            $article = $master_detail.find(article_selector);

        $master_detail.find('article').removeClass('grow fadeIn');

        $article.addClass('grow fadeIn');
    });

});

function refreshContactList(response) {
    var contactsList = JSON.parse(response);

    contactsList.forEach(function(element, index, array){
        $('#contacts').append("<button type=\"button\" class=\"list-group-item\" data-option=\"option1\">"+element.firstName+' '+element.secondName+"</button>")
    });
}

function callEndpoint(url, payload, handler) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            handler(this.responseText);
        }
    };
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(payload));
}
