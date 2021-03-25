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

    setTimeout(function(){$(".ya-site-form__submit").val("ПОИСК")}, 300);
});