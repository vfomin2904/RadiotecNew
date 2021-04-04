$(document).ready(function($) {

    // Клик по ссылке "Закрыть".
    $('.popup-close').click(function() {
        $(this).parents('.popup-fade').fadeOut();
        return false;
    });
    $('.popup-open').click(function() {
        $(this).parent().next('.popup-fade').fadeIn();
        return false;
    });

    // Закрытие по клавише Esc.
    $(document).keydown(function(e) {
        if (e.keyCode === 27) {
            e.stopPropagation();
            $('.popup-fade').fadeOut();
        }
    });

    // Клик по фону, но не по окну.
    $('.popup-fade').click(function(e) {
        if ($(e.target).closest('.popup').length == 0) {
            $(this).fadeOut();
        }
    });

    $(".subscribe_button").click(function(){
        var form = $('.user_form')[0];

        var data = new FormData(form);
        $('.form_message').html("");
        var url = $('.user_form').attr("action");

        for(i = 0; i < $('.required').length; i++){
            if($('.required').eq(i).val().length<1){
                $(".form_message").html('<div class="ajax_error">Ошибка. Не заполнены обязательные поля.</div>');
                return;
            }
        }

        $.ajax({
            url: url,
            type: "POST", //метод отправки
            data: $('.user_form').serialize(),
            success: function (response) {
                $(".form_message").html('<div class="ajax_success">Заказ успешно отправлен. Скоро наши менеджеры свяжутся с Вами!</div>');
                $(".subscribe_button").hide();
            },
            error: function (response) {
                $(".form_message").html('<div class="ajax_error">Ошибка. Данные не отправлены.</div>');
            }
    });});
});

function getAllUrlParams(url) {

    // извлекаем строку из URL или объекта window
    var queryString = url ? url.split('?')[1] : window.location.search.slice(1);

    // объект для хранения параметров
    var obj = {};

    // если есть строка запроса
    if (queryString) {

        // данные после знака # будут опущены
        queryString = queryString.split('#')[0];

        // разделяем параметры
        var arr = queryString.split('&');

        for (var i=0; i<arr.length; i++) {
            // разделяем параметр на ключ => значение
            var a = arr[i].split('=');

            // обработка данных вида: list[]=thing1&list[]=thing2
            var paramNum = undefined;
            var paramName = a[0].replace(/\[\d*\]/, function(v) {
                paramNum = v.slice(1,-1);
                return '';
            });

            // передача значения параметра ('true' если значение не задано)
            var paramValue = typeof(a[1])==='undefined' ? true : a[1];

            // преобразование регистра
            paramName = paramName.toLowerCase();
            paramValue = paramValue.toLowerCase();

            // если ключ параметра уже задан
            if (obj[paramName]) {
                // преобразуем текущее значение в массив
                if (typeof obj[paramName] === 'string') {
                    obj[paramName] = [obj[paramName]];
                }
                // если не задан индекс...
                if (typeof paramNum === 'undefined') {
                    // помещаем значение в конец массива
                    obj[paramName].push(paramValue);
                }
                // если индекс задан...
                else {
                    // размещаем элемент по заданному индексу
                    obj[paramName][paramNum] = paramValue;
                }
            }
            // если параметр не задан, делаем это вручную
            else {
                obj[paramName] = paramValue;
            }
        }
    }

    return obj;
}