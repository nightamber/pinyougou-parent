var app = angular.module('pinyougou',[]);
app.filter('trustHtml',['$sce',function ($sce) {
    /*$sce 服务写成过滤器*/
    return function (data) {//传入参数是被过滤的内容
        return $sce.trustAsHtml(data);//返回是过滤后的内容(信任html的转换)
    }
}]);