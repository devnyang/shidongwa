function $(id){
	return document.getElementById(id);
}
function showDescription(id){
	$(id).style.display="block";
	$("button" + id).value = "Hide";
	$("button" + id).setAttribute("onclick", "hideDescription('" + id + "')");
}
function hideDescription(id){
	$(id).style.display="none";
	$("button" + id).value = "Show";
	$("button" + id).setAttribute("onclick", "showDescription('" + id + "')");        	
}

function loadEntries(){
	if (!window.JSON){
		var head = document.getElementsByTagName("head")[0];
		var jsScript = document.createElement("script");
		jsScript.setAttribute("src", "json2.js");
		jsScript.setAttribute("type","text/javascript");
		head.appendChild(jsScript);
	}
	var localEntries = loadLocal();
	var newest = 0;
	if (localEntries.length > 0){
		newest = localEntries[0].id;
	}
	var i = 0;
	for (i=0;i<localEntries.length;i++){
		addEntry(localEntries[i]);
	}
	if (navigator.onLine){
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function(){
			if (this.readyState == 4 && this.status == 200){
	   	   		var theFeed = JSON.parse(this.responseText);
	   	   		var i = 0;
	   	   		if (theFeed.entries){
	   	   	   		var len = theFeed.entries.length;
	   	   	   		for (i=0;i<len;i++){
	   	   	   	   		addEntry(theFeed.entries[len - 1 -i], true);
	   	   	   	   		saveLocal(theFeed.entries[len - 1 -i]);
	   	   	   		}
	   	   		}
	   	   		var body = document.getElementsByTagName("body")[0];
	   	   		body.removeChild($("loader"));
	   	   	}
		};
		var urlStr = "/resources/feed?after=" + newest;
		//alert("urlStr=" + urlStr);
		xhr.open("GET", urlStr);
		xhr.send();	
	}else{
		alert("navigator is off line!");
	}
}
function saveLocal(entry){
	if (!window.localStorage){
		return;
	}
	localStorage.setItem(entry["id"], JSON.stringify(entry));
}
function loadLocal(){
	if (!window.localStorage){
		return [];
	}
	var i = 0;
	var e = {};
	var id = 0;
	var entries = [];
	
	//alert("localStorage.length=" + localStorage.length)
	for (i=0; i< localStorage.length; i++){
		id = localStorage.key(i);
		e = JSON.parse(localStorage.getItem(id));
		entries[entries.length] = e;
	}
	entries.sort(function(a,b) { 
		return b.id - a.id; 
	});
	return entries;
}
function addEntry(e, prepend){
	var eDiv = document.createElement("div");
	eDiv.setAttribute("class", "item");
	var link = document.createElement("a");
	link.setAttribute("href", e["link"]);
	var title = document.createTextNode(e["title"]);
	link.appendChild(title);
	eDiv.appendChild(link);
	var button = document.createElement("input");
	button.setAttribute("type", "button");
	button.setAttribute("value", "Show");
	button.setAttribute("id", "button" + e["id"]);
	button.setAttribute("onclick", "showDescription('" + e["id"] + "')");
	eDiv.appendChild(button);
	var dDiv = document.createElement("div");
	dDiv.setAttribute("id", e["id"]);
	dDiv.setAttribute("class", "description");
	dDiv.setAttribute("style", "display:none");
	dDiv.innerHTML = e["description"];
	eDiv.appendChild(dDiv);
	var body = document.getElementsByTagName("body")[0];
	if (!prepend && body.childNodes.length > 1){
    	body.appendChild(eDiv);
	} else {
    	body.insertBefore(eDiv, body.childNodes.item(2));
	}
}