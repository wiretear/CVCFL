var MyBank = {};
MyBank.SubmitionProgress = function(){
    var submitted = false;
    this.doSubmit = function(){
        if (!submitted) {
            disableAllLinks();
            var f = document.forms[0];
            var winHeight = 100;
            var winWidth = 250;
            var Top = ((getInsideWindowHeight()/2) - (winHeight / 2));
            var Left = ((getInsideWindowWidth()/2) - (winWidth / 2));

            submitted = true;
            var div = document.createElement('div');
            var para = document.createElement('p');
            para.textContent = 'Please Wait...';
            para.innerText = 'Please Wait...';
            div.appendChild(para);
            var divStyle = div.style;
            divStyle.backgroundColor = '#1FF';
            divStyle.width = winWidth + 'px';
            divStyle.heigth = winHeight + 'px';
            divStyle.fontSize = '32px';
            divStyle.position = 'absolute';
            divStyle.zIndex = 0;
            divStyle.left = Left;
            divStyle.top = Top;
            var firstNode = f.firstChild;
            f.insertBefore(div, firstNode);
            return true;
        } else {
            return false;
        }
    }
    disableAllLinks = function(){
        var allLinks = document.links;
        for(var i = 0; i < allLinks.length; i++){
            var aLink = allLinks.item(i);
            aLink.onclick = null;
        }
    }
    getInsideWindowWidth = function() {
        if (window.innerWidth) {
            return window.innerWidth;
        } else if (document.body.scrollWidth) {
            // measure the html element's clientHeight
            return document.body.scrollWidth;
        } else if (document.width) {
            return document.width;
        }
        return 0;
    }
    getInsideWindowHeight = function() {
        if (window.innerHeight) {
            return window.innerHeight;
        } else if (document.body.scrollHeight) {
            // measure the html element's clientHeight
            return document.body.scrollHeight;
        } else if (document.height) {
            return document.height;
        }
        return 0;
    }
};