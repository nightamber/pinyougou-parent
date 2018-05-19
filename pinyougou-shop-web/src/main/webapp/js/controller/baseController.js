app.controller('baseController',function ($scope) {

    $scope.reloadList=function(){
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage);
    }

    $scope.paginationConf={
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10,20,30,40,50],
        onChange: function () {
            $scope.reloadList();
        }
    }

    $scope.selectIds=[];
    $scope.updateSelection = function ($event,id) {
        if($event.target.checked){
            $scope.selectIds.push(id);
        }else {
            var idx = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx,1);
        }
    }

    $scope.jsonToString=function (jsonString,key) {
        var json=JSON.parse(jsonString);
        var value="";
        for (var i=0;i<json.length;i++){
                if(i>0){
                    value += ",";
                }
            value += json[i][key];
        }

        return value;
    }
    //List集合中查询某key的值
    $scope.searchObjectByKey=function (list,key,keyValue) {
        for(var i=0; i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }
});