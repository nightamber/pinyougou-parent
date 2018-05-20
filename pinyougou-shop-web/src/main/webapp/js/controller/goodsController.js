 //控制层 
app.controller('goodsController' ,function($scope,$controller ,$location  ,goodsService,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(){
		var id = $location.search()['id'];

		if(id==null){
			return;
		}

		goodsService.findOne(id).success(
			function(response){

				$scope.entity= response;
				editor.html($scope.entity.goodsDesc.introduction);//设置商品描述
				//商品图片
                $scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);
				//扩展属性
				$scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);
				//规格转换
				$scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);
				//转换SKU列表中的规格对象
				for(var i=0;i<$scope.entity.itemList.length;i++){
					$scope.entity.itemList[i].spec=JSON.parse($scope.entity.itemList[i].spec);
				}

			}
		);
	}
	
	//保存 
	$scope.save=function(){
        $scope.entity.goodsDesc.introduction=editor.html();
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
                    alert("保存成功");
                  	location.href="goods.html";
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//添加
    $scope.add=function(){
		$scope.entity.goodsDesc.introduction=editor.html();


        goodsService.add($scope.entity).success(
            function(response){
                if(response.success){
                    alert("新增成功");
                    $scope.entity={};
                    editor.html("");//清空编辑器
                }else{
                    alert(response.message);
                }
            }
        );
    }

    //上传图片
    $scope.uploadFile=function () {
		uploadService.uploadFile().success(
			function (response) {
				if(response.success){
					$scope.image_entity.url=response.message;
				}else {
					alert(response.message);
				}
            }
	)
    }


    $scope.entity={goods:{},goodsDesc:{itemImages:[],specificationItems:[]}};

	//将当前上传的图片实体存入图片列表
    $scope.add_image_entity=function () {
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }


    $scope.remove_image_entity=function(index){
    	$scope.entity.goodsDesc.itemImages.splice(index,1);
	}

	//查询一级分类商品列表
	$scope.selectItemCat1List=function () {
		itemCatService.findByParentId(0).success(
			function (response) {
				$scope.selectCat1List=response
            }
		)
    }
	//查询二级分类列表
    $scope.$watch('entity.goods.category1Id',function (newValue,oldValue) {
		itemCatService.findByParentId(newValue).success(
			function (response) {
                $scope.selectCat2List=response;
            }

		)
    })

    //查询三级分类列表
    $scope.$watch('entity.goods.category2Id',function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.selectCat3List=response;
            }

        )
    })

    //查询模版ID
    $scope.$watch('entity.goods.category3Id',function (newValue,oldValue) {
        itemCatService.findOne(newValue).success(
        	function (response) {
				$scope.entity.goods.typeTemplateId=response.typeId;
            }
        	
		)
    })

    //读取模版ID 查询相对的品牌列表 + 读取拓展属性+扩展属性
    $scope.$watch('entity.goods.typeTemplateId',function (newValue,oldValue) {
       typeTemplateService.findOne(newValue).success(

       	function (response) {
			$scope.typeTemplate=response;//模版对象
			//品牌列表类型转换
            $scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);

			//扩展属性
			if($location.search()['id']==null){//增加商品
                $scope.entity.goodsDesc.customAttributeItems= JSON.parse($scope.typeTemplate.customAttributeItems);
			}




        }
	   )

		typeTemplateService.findSpecList(newValue).success(
			function (response) {
				$scope.specList=response;
            }
		);
    })



	$scope.updateSpecAttribute=function ($event,name,value) {
		var object = $scope.searchObjectByKey($scope.entity.goodsDesc.specificationItems,'attributeName',name);
   		if(object!=null){
   			if($event.target.checked){
                object.attributeValue.push(value);
			}else {//取消勾选
                object.attributeValue.splice(object.attributeValue.indexOf(value),1);
				//如果选项都取消，删除词条
                if(object.attributeValue.length==0){
                    $scope.entity.goodsDesc.specificationItems.splice(
                    	$scope.entity.goodsDesc.specificationItems.indexOf(object),1
					);
				}
			}

		}else {
            $scope.entity.goodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
		}
    }


	//创建SKU列表
	$scope.createItemList=function () {
		$scope.entity.itemList=[{spec:{},price:0,num:9999,status:'0',isDefault:'0'}];//初始化列表

        var items = $scope.entity.goodsDesc.specificationItems;

        for(var i=0;i<items.length;i++) {
			$scope.entity.itemList=addColumn($scope.entity.itemList,items[i].attributeName,items[i].attributeValue);

        }





    }



    addColumn=function(list,columnName,columnValues) {
		var newList=[];
		//进行深度克隆
		for(var i=0;i<list.length;i++){
			var oldRow = list[i];
			for(var j=0;j<columnValues.length;j++){
				var newRow=JSON.parse(JSON.stringify(oldRow));
				newRow.spec[columnName]=columnValues[j];
				newList.push(newRow);
			}
		}
		return newList;
    }

    $scope.status=['未审核','已审核','审核未通过','已关闭'];
    $scope.itemCatList=[];
    //查询商品分类列表
	$scope.findItemCatList=function () {
		itemCatService.findAll().success(
			function (response) {
				for (var i=0;i<response.length;i++){
					$scope.itemCatList[response[i].id]=response[i].name;
				}
            }
		);
    }
	//判断规格与规格选项是否应该被勾选
   $scope.checkAttributeValue=function (specName,optionName) {
       var items = $scope.entity.goodsDesc.specificationItems;

		var object = $scope.searchObjectByKey(items,'attributeName',specName);
		if(object != null){
			//如果可以查询到规格
			if(object.attributeValue.indexOf(optionName)>0){
				return true;

			}else {
				return false;
			}
		}else {
            return false;
		}


   }


});	
