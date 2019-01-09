$(function() {

    $.ajax({
        url : "news",
        type : "get",
        success : function(data) {
            $("#newstable").html("");
            $.each(data, function(i, news) {
                var timeStr = calInterval(news.time);
                var html = "";
                html += "<tr><td><a href='" + news.url + "'>" + news.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + timeStr + "</td></tr>";
                html += "<tr><td>" + news.brief + "</td></tr>";
                html += "<tr><td>来源：<a href='" + news.siteAddr + "'>" + news.siteName + "</a></td></tr>";
                $("#newstable").append(html);
            });
        }
    });
});

function calInterval(time) {
    var now = new Date();
    var time = new Date(time.replace(/-/g, "/"));
    var interval = (now - time) / 1000;
    if (interval > 30 * 60 && interval < 60 * 60) {
        return "半小时前";
    }
    if (interval < 30 * 60) {
        return Math.ceil(interval / 60) + "分钟前";
    }
    if (interval > 60 * 60) {
        return Math.ceil(interval / 3600) + "小时前";
    }
}