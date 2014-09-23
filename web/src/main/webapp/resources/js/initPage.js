var fontsize = getCookie("fontsize");
if (fontsize !== -1) {
    $("body").css("font-size", fontsize);
}
function getCookie(c_name) {
    var c_start = document.cookie.indexOf(c_name + "=");
    if (c_start !== -1) {
        c_start = c_start + c_name.length + 1;
        var c_end = document.cookie.indexOf(";", c_start);
        if (c_end === -1)
            c_end = document.cookie.length;
        return unescape(document.cookie.substring(c_start, c_end));
    }
    return -1;
}

