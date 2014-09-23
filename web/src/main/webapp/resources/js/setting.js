//初始化页面
initPage();
function initPage() {
    var online = getCookie("online");
    if (online !== -1) {
        $("#line" + online).addClass("cur");
    } else {
        $("#line360").addClass("cur");
    }
    var download = getCookie("download");
    if (download !== -1) {
        $("#down" + download).addClass("cur");
    } else {
        $("#down360").addClass("cur");
    }
    var fontsize = getCookie("fontsize");
    if (fontsize !== -1) {
        $("#font" + fontsize).addClass("cur");
        $("body").css("font-size", fontsize);
    } else {
        $("#font14px").addClass("cur");
    }
}

function setOnline(data) {
    setCookie("online", data);
    $("#line720,#line480,#line360,#line180").removeClass("cur");
    initPage();
}

function setDown(data) {
    setCookie("download", data);
    $("#down720,#down480,#down360,#down180").removeClass("cur");
    initPage();
}

function setFont(data) {
    setCookie("fontsize", data);
    $("#font14px,#font18px,#font24px,#font28px").removeClass("cur");
    initPage();
}

function setCookie(name, value) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + 10 * 365);
    document.cookie = name + "=" + value + ";expires=" + exdate.toGMTString();
}