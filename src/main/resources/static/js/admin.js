$(document).ready(function(){/* кода документ загрузится вызывается функция */
    loadPages();/* подгружаем страници */
    loadData(0);/* подгружаем данные для нулевой страници */
});

function loadPages() {/*  */
    $.getJSON('/admin/count', function(data) {/* делаем get-запрос на энд-поинт '/admin/count' , результат прилетает в виде json обьекта*/
        var pageCount = (data.count / data.pageSize) +
            (data.count % data.pageSize > 0 ? 1 : 0);/* считаем сколько кнопок нам нужно, где data.count - количество геолокаций, data.pageSize - количество геолокаций на странице*/
        var i;

        for (i = 1; i <= pageCount; i++) {/* проходим циклом по '#pages' */
            $('#pages').append(
                $('<li>').attr('class', 'page-item').append(/* добавляем элемент */
                    $('<a>').attr('class', 'page-link').attr('id', i - 1)/* добавляем внутрь ссылку с атрибутом 'id'= i - 1 */
                        .append('Page ' + i))/* добавляем внутрь ссылки текст: 'Page ' + i */
            );
        }
    });

    $("#pages").on("click", ".page-link", function(event) {/* если кликнули на элемент с id-шником "#pages",у которого стиль ".page-link", запускаем функцию */
        loadData(event.target.id);/*подгружаем данные,   event.target.id - возвращает id-шник нажатой кнопки*/
    });
}

function loadData(page) {/* подгружаем данные для нулевой страници */
    $("#data > tbody").empty();/* находим внутри таблици с id-шником #data тег tbody,   empty() - чистит все что находится внутри этого тега*/

    $.getJSON('/admin/geo?page=' + page, function(data) {/* делаем запрос на '/admin/geo?page=' и подставляем номер страницы, вернется data - масив json-ов */
        var i;

        for (i = 0; i < data.length; i++) {/* проходим циклом по масиву*/
            $('#data > tbody:last-child').append(/* в таблице '#data, в ее теге tbody берем последний элемент и добавляем ему строку */
                $('<tr>')/* внутрь строки добавляем колонки */
                    .append($('<td>').append(data[i].ip))/* внутрь колонок добавляем данные */
                    .append($('<td>').append(data[i].city))
                    .append($('<td>').append(data[i].region))
                    .append($('<td>').append(data[i].country))
            );
        }
    });
}