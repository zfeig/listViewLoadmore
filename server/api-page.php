<?php
header("Content-Type:application/json;charset=utf-8");

class Page {
	 private $data;
	 private $page = 1;  
	 private $pageSize = 10;
	 private $result = [
		"status" => 200,
		"msg" => "获取成功",
		"result" => []
	];
     
     function __construct(){
         $this->data =  require  __DIR__.DIRECTORY_SEPARATOR."data".DIRECTORY_SEPARATOR."page.php";
         $this->getDataByPage();
         $this->getResponse();
     }


	  function encodeCN($result){
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
	 *@输出结果
	 *
	 **/
     private function getResponse(){
     	//格式化数据结果
		$this->result = $this->encodeCN($this->result);
		echo urldecode(json_encode($this->result));
     }


    /**
	* @getDataByPage
	**/
	private function  getDataByPage(){
			//获取分页数据
		$this->page = isset($_GET['page'])? intval($_GET['page']):$this->page;
		$start = ($this->page-1)*$this->pageSize;
		$end = $start + $this->pageSize-1;
	    
	    if($this->page<1){
	    	$this->result['status'] = 400;
			$this->result['msg'] = "参数错误";
			$this->result['result'] = [];
	    }else if($end >= count($this->data)){
			$this->result['status'] = 300;
			$this->result['msg'] = "没有更多数据了";
			$this->result['result'] = [];
		}else{
			for($i = $start; $i<=$end; $i++){
				array_push($this->result['result'],$this->data[$i]);
			}
		}
	}
}
 new Page();
?>