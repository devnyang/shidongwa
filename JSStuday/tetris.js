/***********************************************************************************
 * @author: sailor   
 * 
 * @version: Tetris1.0
 * 
 * date : 2010-04-05
 * 
 * E-Mail:zpsailor@yahoo.com.cn
 * 
 * QQ:251396377
 ***********************************************************************************/
var cols=12,rows=20,grid=20;
var type=-1,nextType;
var curSqure,nextSqure;//��ǰͼ�ο����һ��ͼ�ο�
var Score=0,dels=0,level=1;//��¼���֣�������������ǰ��Ϸ����
var MyTimer;
var speed = new Array(500,450,400,350,300,250,200,150,100);
var myColor=new Array("gray","cyan","cyan","cyan","cyan","cyan","cyan","cyan");

function $(id){return document.getElementById(id);}

function CreateArea(rows,cols,name){
	var str="<table align='center' border='1' style=\"background: "+myColor[0]+"\">";
	for(var i=0;i<rows;i++){
    str+="<tr height="+grid+">";
      for(var j=0;j<cols;j++){
      	var id=name+i+"#"+j;
      	str+="<td id="+id+" width="+grid+" class='gridStyle' style=\"background: "+myColor[0]+"\"></td>"
      }
      str+="</tr>";
	}
	str+="</table>";
    return str;
}

/**
 * ��ʼ����Ϸ
 */
function InitGame(){
 $("GameBody").innerHTML=CreateArea(rows,cols,"gameGrid");
 $("ForeBody").innerHTML=CreateArea(4,4,"foreGrid");
 	type=parseInt((Math.random()*7))+1;
 	var mainSqure=chooseSqure(type,cols/2,1);
 	nextType=parseInt((Math.random()*7))+1;
 	var foreSqure=chooseSqure(nextType,4/2-1,1);
 	reDraw(mainSqure,"gameGrid");
 	reDraw(foreSqure,"foreGrid");
 	curSqure=mainSqure;
 	nextSqure=foreSqure;
}

/**
 * �漴����ͼ��
 */
function chooseSqure(type,x,y){
var mySqure=new Array(4);
switch(type){
	 case 1: 
	 case 15:
	         mySqure[0]=new Squre(x-1,y,1);
	         mySqure[1]=new Squre(x,y,1);
	         mySqure[2]=new Squre(x+1,y,1);
	         mySqure[3]=new Squre(x+2,y,1);
	         break;
	         
	 case 2: 
	 case 9:
	 case 16:
	 case 23:
	         mySqure[0]=new Squre(x,y-1,2);
	         mySqure[1]=new Squre(x,y,2);
	         mySqure[2]=new Squre(x+1,y-1,2);
	         mySqure[3]=new Squre(x+1,y,2);
	         break;
	      
	 case 3: 
	         mySqure[0]=new Squre(x,y-1,3);
	         mySqure[1]=new Squre(x,y,3);
	         mySqure[2]=new Squre(x,y+1,3);
	         mySqure[3]=new Squre(x+1,y,3);
	         break;
	      
	 case 4: 
	 case 18:
	         mySqure[0]=new Squre(x,y-1,4);
	         mySqure[1]=new Squre(x,y,4);
	         mySqure[2]=new Squre(x+1,y,4);
	         mySqure[3]=new Squre(x+1,y+1,4);
	         break;
	        
	 case 5: 
	 case 19:
	         mySqure[0]=new Squre(x+1,y-1,5);
	         mySqure[1]=new Squre(x,y,5);
	         mySqure[2]=new Squre(x+1,y,5);
	         mySqure[3]=new Squre(x,y+1,5);
	         break;
	         
	 case 6: 
	         mySqure[0]=new Squre(x+1,y-1,6);
	         mySqure[1]=new Squre(x,y,6);
	         mySqure[2]=new Squre(x,y-1,6);
	         mySqure[3]=new Squre(x,y+1,6);
	         break;
	         
	 case 7: 
	         mySqure[0]=new Squre(x-1,y-1,7);
	         mySqure[1]=new Squre(x,y,7);
	         mySqure[2]=new Squre(x,y-1,7);
	         mySqure[3]=new Squre(x,y+1,7);
	         break;
	 
	 case 8:
	 case 22:
	         mySqure[0]=new Squre(x,y-1,1);
	         mySqure[1]=new Squre(x,y,1);
	         mySqure[2]=new Squre(x,y+1,1);
	         mySqure[3]=new Squre(x,y+2,1);
	         break;
	         
	 case 10: 
	         mySqure[0]=new Squre(x-1,y,3);
	         mySqure[1]=new Squre(x,y,3);
	         mySqure[2]=new Squre(x+1,y,3);
	         mySqure[3]=new Squre(x,y+1,3);
	         break;
	         
	 case 11: 
	 case 25:
	         mySqure[0]=new Squre(x-1,y+1,4);
	         mySqure[1]=new Squre(x,y,4);
	         mySqure[2]=new Squre(x,y+1,4);
	         mySqure[3]=new Squre(x+1,y,4);
	         break;
	         
	 case 12:
	 case 26: 
	         mySqure[0]=new Squre(x-1,y,5);
	         mySqure[1]=new Squre(x,y,5);
	         mySqure[2]=new Squre(x,y+1,5);
	         mySqure[3]=new Squre(x+1,y+1,5);
	         break;
	         
	 case 13: 
	         mySqure[0]=new Squre(x-1,y,6);
	         mySqure[1]=new Squre(x,y,6);
	         mySqure[2]=new Squre(x+1,y,6);
	         mySqure[3]=new Squre(x+1,y+1,6);
	         break;
	         
	 case 14: 
	         mySqure[0]=new Squre(x+1,y-1,7);
	         mySqure[1]=new Squre(x,y,7);
	         mySqure[2]=new Squre(x+1,y,7);
	         mySqure[3]=new Squre(x-1,y,7);
	         break;
	         
	 case 17: 
	         mySqure[0]=new Squre(x-1,y,3);
	         mySqure[1]=new Squre(x,y,3);
	         mySqure[2]=new Squre(x,y-1,3);
	         mySqure[3]=new Squre(x,y+1,3);
	         break;
	         
	 case 20: 
	         mySqure[0]=new Squre(x-1,y,6);
	         mySqure[1]=new Squre(x,y,6);
	         mySqure[2]=new Squre(x,y-1,6);
	         mySqure[3]=new Squre(x,y-2,6);
	         break;
	         
	 case 21: 
	         mySqure[0]=new Squre(x,y-1,7);
	         mySqure[1]=new Squre(x,y,7);
	         mySqure[2]=new Squre(x,y-2,7);
	         mySqure[3]=new Squre(x+1,y,7);
	         break;
	         
	 case 24: 
	         mySqure[0]=new Squre(x-1,y,3);
	         mySqure[1]=new Squre(x,y,3);
	         mySqure[2]=new Squre(x+1,y,3);
	         mySqure[3]=new Squre(x,y-1,3);
	         break;
	      
	 case 27: 
	         mySqure[0]=new Squre(x,y-1,6);
	         mySqure[1]=new Squre(x,y,6);
	         mySqure[2]=new Squre(x+1,y,6);
	         mySqure[3]=new Squre(x+2,y,6);
	         break;
	         
	 case 28: 
	         mySqure[0]=new Squre(x,y-1,7);
	         mySqure[1]=new Squre(x,y,7);
	         mySqure[2]=new Squre(x+1,y-1,7);
	         mySqure[3]=new Squre(x+2,y-1,7);
	         break;
	 
  }	
  return mySqure;
}

function Squre(col,row,color){
	this.col=col;
	this.row=row;
	this.color=color;
}

/**
 * �ػ�
 */
function reDraw(squre,name){
	var area=$(name);
	for(var i=0;i<squre.length;i++){
       var id=name+squre[i].row+"#"+squre[i].col;
      // alert(id);
       $(id).style.background=myColor[squre[i].color];		
	}
}

function clearDraw(squre,name){
	var area=$(name);
	for(var i=0;i<squre.length;i++){
       var id=name+squre[i].row+"#"+squre[i].col;
      // alert(id);
       $(id).style.background=myColor[0];		
	}
}

function keyDown(){
	switch(event.keyCode){
		case 38: overTurn();break;
		case 40: moveCurSqure(0,1,"down");break;
		case 37: moveCurSqure(-1,0,"left");break;
		case 39: moveCurSqure(1,0,"right");;break;
	}
}

/**
 * �ƶ���ǰͼ��
 */
function moveCurSqure(x,y,kind){
  var nextChange=new Array(4);
  for(var i=0;i<curSqure.length;i++){
  nextChange[i]=new Squre(curSqure[i].col+x,curSqure[i].row+y,curSqure[i].color);
  }
  
  if(isBound(kind,nextChange)&&kind=="down"){
  	if(isGameOver()){
  		window.clearInterval(MyTimer);
  		alert("��Ϸ����!");
  		return ;
  	}
  	deleteRows();//ִ��������
  	doSwitch();
  }
  
  if(isBound(kind,nextChange)){
  	return ;
  }
  
  clearDraw(curSqure,"gameGrid");
  
  //�������Ҫ�ƶ�����һ��λ���Ѿ������谭��ϰ������������࣬��ܾ��ƶ����ڵ׶ˣ���ǰͼ���䶨���л���һͼ��
  for(var i=0;i<nextChange.length;i++){
  	var id="gameGrid"+nextChange[i].row+"#"+nextChange[i].col;
       if($(id).style.background!=myColor[0]){
       	if(kind=="down"){
       		reDraw(curSqure,"gameGrid");
       		if(isGameOver()){
       		 window.clearInterval(MyTimer);
  		     alert("��Ϸ����!");
  		     return ;
  	        }
  	        deleteRows();//ִ��������
       		doSwitch();
       	}
       	reDraw(curSqure,"gameGrid");
       	return;
       }		
  }
  
  reDraw(nextChange,"gameGrid");
  curSqure=nextChange;
}

/**
 * �ж�ͼ���Ƿ��˱߽���
 */
function isBound(kind,squre){
	var theSqure=squre;
	var downMax=Math.max(Math.max(theSqure[0].row,theSqure[1].row),Math.max(theSqure[2].row,theSqure[3].row));
	var rightMax=Math.max(Math.max(theSqure[0].col,theSqure[1].col),Math.max(theSqure[2].col,theSqure[3].col));
	var leftMax=Math.min(Math.min(theSqure[0].col,theSqure[1].col),Math.min(theSqure[2].col,theSqure[3].col));
	var upMax=Math.min(Math.min(theSqure[0].row,theSqure[1].row),Math.min(theSqure[2].row,theSqure[3].row));
	
	if(downMax>rows-1&&kind=="down"){
	  	return true;
	}
	if(rightMax>cols-1&&kind=="right"){
		return true;
	}
	if(leftMax<0&&kind=="left"){
		return true;
	}
	if(upMax<0&&kind=="up"){
		return true;
	}
	return false;
}

/**
 * �ж�ͼ����ʾ�����Ƿ����ϰ���
 */
function haveObstacle(kind,squre){
	var theSqure=squre;
	var downMax=Math.max(Math.max(theSqure[0].row,theSqure[1].row),Math.max(theSqure[2].row,theSqure[3].row));
	var rightMax=Math.max(Math.max(theSqure[0].col,theSqure[1].col),Math.max(theSqure[2].col,theSqure[3].col));
	var leftMax=Math.min(Math.min(theSqure[0].col,theSqure[1].col),Math.min(theSqure[2].col,theSqure[3].col));
	var upMax=Math.min(Math.min(theSqure[0].row,theSqure[1].row),Math.min(theSqure[2].row,theSqure[3].row));
	clearDraw(curSqure,"gameGrid");
	for(var i=0;i<theSqure.length;i++){
		var id="gameGrid"+theSqure[i].row+"#"+theSqure[i].col;
		//alert(id);
		if($(id).style.background!=myColor[0]){
			if(kind=="left"&&theSqure[i].col<rightMax){
				reDraw(curSqure,"gameGrid");
				return true;
			}
			if(kind=="right"&&theSqure[i].col>leftMax){
				reDraw(curSqure,"gameGrid");
				return true;
			}
			if(kind=="down"&&theSqure[i].col<downMax){
				reDraw(curSqure,"gameGrid");
				return true;
			}
	}		
	}
	return false;
} 


/**
 * һ��ͼ�����䶨���л�����һ��ͼ��
 */
function doSwitch(){
	type=nextType;
	nextType=parseInt((Math.random()*7))+1;
	var mainSqure=chooseSqure(type,cols/2,1);
	var foreSqure=chooseSqure(nextType,4/2-1,1);
 	reDraw(mainSqure,"gameGrid");
 	clearDraw(nextSqure,"foreGrid");
 	reDraw(foreSqure,"foreGrid");
 	curSqure=mainSqure;
 	nextSqure=foreSqure;
 	window.clearInterval(MyTimer);
	startGame();
}

/**
 * ʵ�ֵ�ǰͼ�εķ�ת
 */
function overTurn(){
	 var nextChange;
	 var oldType=type;
     type+=7;
	 if(type>28){
	 	type-=28;
	 }
	 var nextChange=chooseSqure(type,curSqure[1].col,curSqure[1].row);
	 
	 //�жϱ߽紦����ת
	 while(isBound("left",nextChange)){
	 	var x=1,y=0;
	 	
	 	for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	}
	 }
	 
	while(isBound("right",nextChange)){
	 	var x=-1,y=0;
	 	for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	}
	 }
	
	while(isBound("up",nextChange)){
	 	var x=0,y=1;
	 	for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	}
	 } 
	 
	while(isBound("down",nextChange)){
	 	var x=0,y=-1;
	 	for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	}
	 } 
	 
	 //�������ϰ��ﴦ����ת
	 if(haveObstacle("left",nextChange)&&haveObstacle("right",nextChange)){
	 	type=oldType;
	 	return ;
	 }
	 if(haveObstacle("left",nextChange)){
	 	while(haveObstacle("left",nextChange)){
	 	  var x=1,y=0;
	 	  for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	 }
	 	  if(isBound("right",nextChange)){
	 	  	type=oldType;
	 	  	return ;
	 	  }else{
	 	  if(haveObstacle("right",nextChange)){
	 	  	type=oldType;
	 	  	return ;
	 	  }
	 	  }
	 	}
	 }
	 
	 if(haveObstacle("right",nextChange)){
	 	while(haveObstacle("right",nextChange)){
	 	  var x=-1,y=0;
	 	  for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	 }
	 	  if(isBound("left",nextChange)){
	 	  	type=oldType;
	 	  	return ;
	 	  }else {
	 	  if(haveObstacle("left",nextChange)){
	 	  	type=oldType;
	 	  	return ;
	 	  }
	 	  }
	 	}
	 }
	 
	 if(haveObstacle("down",nextChange)){
	 	while(haveObstacle("down",nextChange)){
	 		var x=0,y=-1;
	 		for(var i=0;i<nextChange.length;i++){
	 		nextChange[i].col+=x;
	 		nextChange[i].row+=y;
	 	 }
	 	 if(isBound("up",nextChange)){
	 	 	type=oldType;
	 	 	return ;
	 	 }
	 	}
	 }
	 clearDraw(curSqure,"gameGrid");
	 curSqure=nextChange;
	 reDraw(curSqure,"gameGrid");
}

/**
 * ��ȥһ�л����
 */
function deleteRows(){
	var delRows=new Array();//��¼��Ҫɾ������
	var downMax=Math.max(Math.max(curSqure[0].row,curSqure[1].row),Math.max(curSqure[2].row,curSqure[3].row));
	var upMax=Math.min(Math.min(curSqure[0].row,curSqure[1].row),Math.min(curSqure[2].row,curSqure[3].row));
	for(var i=upMax;i<=downMax;i++){
		var mark=true;
		for(var j=0;j<cols;j++){
			var id="gameGrid"+i+"#"+j;
			if($(id).style.background==myColor[0]){
				mark=false;
			}
		}
		if(mark)
		{
			delRows.push(i);
		}
	}

  //ִ��ɾ��
  for(var i=0;i<delRows.length;i++){
  	for(var j=0;j<cols;j++){
			var id="gameGrid"+delRows[i]+"#"+j;
			$(id).style.background=myColor[0];
		}
  }
  if(delRows.length>0)
   moveRows(delRows);
}

/**
 * �ƶ�һ�л����
 */
function moveRows(moveR){
var mRows=moveR.length;

Score+=mRows*100+parseInt(mRows/2)*100;
dels+=mRows;
level=parseInt(Score/2000)+1;
if(level>8){
	level-=8;
}
$("curLevel").innerText=level;
$("curScore").innerText=Score;
$("curDelRows").innerText=dels;
window.clearInterval(MyTimer);
startGame();
//���������е����沿���ƶ�����
for(var n=0;n<mRows;n++){
var upRows=moveR[n]-1;
for(var i=upRows;i>0;i--)
  for(var j=0;j<cols;j++){
     var id="gameGrid"+i+"#"+j;
     var moveID="gameGrid"+(1+i)+"#"+j;
     $(moveID).style.background=$(id).style.background;
     $(id).style.background=myColor[0]; 	
	 }
 }
}

/**
 * ��ʼ��Ϸ
 */
function startGame(){
 MyTimer=window.setInterval("moveCurSqure(0,1,'down')",speed[level-1]);	
}

/**
 * ��Ϸ��ͣ
 */
function pause(){
	window.clearInterval(MyTimer);
}

function stopGame(){
	if(window.confirm("ȷ��������Ϸ?")){
		window.close();
	}
}

/**
 * �ж���Ϸ�Ƿ����
 */
function isGameOver(){
 var downMin=Math.min(Math.min(curSqure[0].row,curSqure[1].row),Math.min(curSqure[2].row,curSqure[3].row));
 if(downMin<=0){
 	return true;
 }
 else
 { 
 	return false;
 }
}