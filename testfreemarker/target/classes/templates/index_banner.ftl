<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="http://www.xuecheng.com/plugins/normalize-css/normalize.css" />
    <link rel="stylesheet" href="http://www.xuecheng.com/plugins/bootstrap/dist/css/bootstrap.css" />
    <link rel="stylesheet" href="http://www.xuecheng.com/css/page-learing-index.css" />
    <link rel="stylesheet" href="http://www.xuecheng.com/css/page-header.css" />
</head>
<body>
<div class="banner-roll">
    <div class="banner-item">
        <#--<div class="item" style="background-image: url(http://www.xuecheng.com/img/widget-bannerB.jpg);"></div>
        <div class="item" style="background-image: url(http://www.xuecheng.com/img/widget-bannerA.jpg);"></div>
        <div class="item" style="background-image: url(http://www.xuecheng.com/img/widget-banner3.png);"></div>
        <div class="item" style="background-image: url(http://www.xuecheng.com/img/widget-bannerB.jpg);"></div>
        <div class="item" style="background-image: url(http://www.xuecheng.com/img/widget-bannerA.jpg);"></div>
        <div class="item" style="background-image: url(http://www.xuecheng.com/img/widget-banner3.png);"></div>-->
        <#if model??>
            <#list model as item>
                 <div class="item" style="background-image: url(${item.value});"></div>
            </#list>
        </#if>
    </div>
    <div class="indicators"></div>
</div>
<script type="text/javascript" src="http://www.xuecheng.com/plugins/jquery/dist/jquery.js"></script>
<script type="text/javascript" src="http://www.xuecheng.com/plugins/bootstrap/dist/js/bootstrap.js"></script>
<script type="text/javascript">
    var tg = $('.banner-item .item');
    var num = 0;
    for (i = 0; i < tg.length; i++) {
        $('.indicators').append('<span></span>');
        $('.indicators').find('span').eq(num).addClass('active');
    }

    function roll() {
        tg.eq(num).animate({
            'opacity': '1',
            'z-index': num
        }, 1000).siblings().animate({
            'opacity': '0',
            'z-index': 0
        }, 1000);
        $('.indicators').find('span').eq(num).addClass('active').siblings().removeClass('active');
        if (num >= tg.length - 1) {
            num = 0;
        } else {
            num++;
        }
    }
    $('.indicators').find('span').click(function() {
        num = $(this).index();
        roll();
    });
    var timer = setInterval(roll, 3000);
    $('.banner-item').mouseover(function() {
        clearInterval(timer)
    });
    $('.banner-item').mouseout(function() {
        timer = setInterval(roll, 3000)
    });
</script>
</body>
</html>