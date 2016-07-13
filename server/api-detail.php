<?php
header("Content-Type:application/json;charset=utf-8");

class pageDetail{
	 
	 private $data;  
	 
	 private $result = [
		"status" => 200,
		"msg" => "获取成功",
		"result" => []
	];
     
     function __construct(){
         $this->data =  require  __DIR__.DIRECTORY_SEPARATOR."data".DIRECTORY_SEPARATOR."page.php";
         $this->getDataById();
         $this->getResponse();
     }

    
    /**
	*@匹配数组里面的值
	*
	*/
	private function hasId($id){
	    $index = null;
		foreach ($this->data as $k => $v) {
			if(isset($this->data[$k]['id']) &&  $this->data[$k]['id'] == $id){
	            $index = $k;
	            break;
			}
		}
		return $index;
	}



     /**
	 *@输出结果
	 *
	 **/
     private function getResponse(){
     	//格式化数据结果
		$this->result = $this->encodeCN($this->result);
		echo urldecode(json_encode($this->result));
     }

     /**
	 *@通过id获取数组值
	 *
	 **/
     private function getDataById(){
		//获取分页数据
		if(isset($_GET['id']) && !empty($_GET['id']) && !is_null(($index = $this->hasId($_GET['id'])))){
			$this->result['result'] = $this->getValueByIndex($index);
		}else{
		    $this->result['status'] = 404;
			$this->result['msg'] = "数据不存在";
			$this->result['result'] = [];
		}     		

     }

	 /**
	 *@转码
	 *
	 **/
	 private function encodeCN($result){

		foreach ($result as $k => $v) {
			if(is_array($v)){
	           $result[$k] = $this->encodeCN($v);
			}else{
				$result[$k] = urlencode($v);
			}
		}

		return $result;
	}


		
		
		/**
		*@根据id获取数组的值
		*
		*/
		private function getValueByIndex($index){
		   $index = intval($index);
		   return $this->data[$index];
		}

}

new pageDetail(); 












    


	

	




?>