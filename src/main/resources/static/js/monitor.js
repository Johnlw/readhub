$(function() {
    $.ajax({
        url : "listMonitorItems",
        type : "get",
        success : function(data) {
            $("#monitor").html("");
            var html = "";
            html += "<tr><td>news spider:</td><td>" + data.nsd + "</td></tr>";
            html += "<tr><td>ip locater:</td><td>" + data.ip + "</td></tr>";
            $("#monitor").append(html);

            $("#monitor").append("<tr><td>News statis(Last 3 Hours):<td></tr>");

            $.each(data.nsList, function(i, n) {
                $("#monitor").append(
                    "<tr><td>News Site:" + n.siteName + "</td><td>Total:" + n.total + "</td><td>New Items:"
                    + n.newItemCount + "</td><td>repetition Rate:" + n.repe_Rate * 100
                    + "%</td><td>Exe time:" + n.exeTime + "</td></tr>");
            });
            $("#monitor").append("<tr><td>Proxy statis(Last 24 Hours):<td></tr>");

            $.each(data.psList, function(i, p) {
                $("#monitor").append(
                    "<tr><td>Proxy Site:" + p.siteName + "</td><td>Total:" + p.totalCount + "</td><td>Valid:"
                    + p.validCount + "</td><td>Valid Rate:" + p.validRate * 100 + "%</td></tr>");
            });

            $.each(data.visitor, function(i, v) {
                $("#monitor").append(
                    "<tr><td>visitors today:</td><td>ip: " + v.ip + " addr: " + v.addr + " count: " + v.count
                    + "</td></tr>");
            });

        }
    });
});