$(document).ready(function () {/*функция которая выполнится как только загрузится index.html*/
    $.getJSON("/rate_USD", function (data) {/*выполнить запрос на енд-поинт '/rate'   data - десиарилизированный результат, который прилетит из RateController */
        $('#buyUSD').text(data.buy);/*на место элемента с id-шником '#rate' в index.html подставляем текст UAH обьекта rates обьекта data*/
        $('#saleUSD').text(data.sale);/*на место элемента с id-шником '#date' в index.html подставляем текст date обьекта data*/
    });
    $.getJSON("/rate_EUR", function (data) {/*выполнить запрос на енд-поинт '/rate'   data - десиарилизированный результат, который прилетит из RateController */
        $('#buyEUR').text(data.buy);/*на место элемента с id-шником '#rate' в index.html подставляем текст UAH обьекта rates обьекта data*/
        $('#saleEUR').text(data.sale);/*на место элемента с id-шником '#date' в index.html подставляем текст date обьекта data*/
    });
});