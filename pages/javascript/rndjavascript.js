


if ( typeof XMLHttpRequest == "undefined" ){
    XMLHttpRequest = function(){
        return new ActiveXObject(
            navigator.userAgent.indexOf("MSIE 5") >= 0 ?
            "Microsoft.XMLHTTP" : "Msxml2.XMLHTTP"
            );
    };
}

//<SCRIPT LANGUAGE='Javascript'>fillout = false; window.onbeforeunload=verifyclose; function verifyclose() { var undefined; var txt; if (document.forms[0] == undefined) return; if (!fillout) { txt = "The qualification form takes 2 minutes to complete."; } return txt; } function voidpopup() { fillout=true; }</script>



(function(){
    document.getElementById('operator').onclick = function(){
        doOperator(this);
    }
    function doOperator(element){
        hide(id("results"));
        //show(id("results"));
        var curLeft = 0;
        var curTop = 0;
        var curBottom = 0;
        var elem = element;
        if(elem.offsetParent){
            do {
                curLeft += elem.offsetLeft;
                curTop += elem.offsetTop;
            } while (elem = elem.offsetParent);
        }
        //alert("Hieght"+element.offsetHeight);
        var div = document.createElement("div");
        div.id = "results";
        div.innerHTML = "<div>Operators:</div><ul></ul>";
        var divStyle = div.style;
        divStyle.position = "absolute";
        divStyle.left = curLeft;
        divStyle.top = curTop + element.offsetHeight;
        divStyle.backgroundColor = "gold";
        divStyle.border = "1px solid blue";
        //document.body.appendChild(div);
        id("formula").parentNode.appendChild(div);
        show(document.getElementById("results"));
        var results = document.getElementById("results").lastChild;
        results.innerHTML = "<li>A</li><li>B</li><li>C</li><li>D</li>";
        document.getElementById("results").onmouseout = function(){
            hide(this);
        }
    }
    function hide(elem){
        var curDisplay = getStyle(elem, 'display');
        if(curDisplay != 'none'){
            elem.$oldDisplay = curDisplay;
            elem.style.display = 'none';
        }
    }
    function show(elem){
        elem.style.display = elem.$oldDisplay || '';
    }
    function id(name){
        return document.getElementById(name);
    }
    function getStyle(elem, name){
        if(elem.style[name]){
            return elem.style[name];
        } else if(elem.currentStyle){
            return elem.currentStyle[name];
        } else if(document.defaultView && document.defaultView.getComputedStyle){
            name = name.replace(/([A-Z])/g, "-$1");
            name = name.toLowerCase();
            var s = document.defaultView.getComputedStyle(elem, "");
            return s && s.getPropertyValue(name);
        } else {
            return null;
        }
    }
})();

function test(){
    "".split("", 3);
}
(function(window, undefined){
    var document = window.document;
    var myBank = (function(){

        var myBank = function(selector){
            return new myBank.fn.init(selector);
        },
        rootmyBank,
        readyBound = false,
        readyList = [],
        DOMContentLoaded,
        trimLeft = /^\s+/,
        trimRight = /\s+$/,
        quickExpr = /^(?:[^<]*(<[\w\W]+>)[^>]*$|#([\w\-]+)$)/,
        trim = String.prototype.trim,
        toString = Object.prototype.toString,
        push = Array.prototype.push,
        class2type = {};
        myBank.fn = myBank.prototype = {
            init : function(args){

                if(myBank.isFunction(args)){
                    //alert('ready');
                    return rootmyBank.ready(args);
                } else {
                    //alert(arguments);
                    this.elements = [];
                    for(var i = 0, len = arguments.length; i < len; i++){
                        var element = arguments[i];
                        if(typeof element === 'string'){
                            element = document.getElementById(element);
                        }
                        this.elements.push(element);
                    }
                }
            },
            selector : "",
            version : "1.0",
            ready: function( fn ) {
                // Attach the listeners
                myBank.bindReady();
                // If the DOM is already ready
                if ( myBank.isReady ) {
                    // Execute the function immediately
                    fn.call( document, myBank );
                // Otherwise, remember the function for later
                } else if ( readyList ) {
                    // Add the function to the wait list
                    readyList.push( fn );
                }
                return this;
            },
            each: function(fn) {
                for ( var i = 0, len = this.elements.length; i < len; ++i ) {
                    fn.call(this, this.elements[i]);
                }
                return this;
            }
        };
        myBank.fn.init.prototype = myBank.fn;

        myBank.isFunction = function(obj) {
            return toString.call(obj) === "[object Function]";
        }

        myBank.ready = function(wait){
            // A third-party is pushing the ready event forwards
            if ( wait === true ) {
                myBank.readyWait--;
            }

            // Make sure that the DOM is not already loaded
            if ( !myBank.readyWait || (wait !== true && !myBank.isReady) ) {
                // Make sure body exists, at least, in case IE gets a little overzealous (ticket #5443).
                if ( !document.body ) {
                    return setTimeout( myBank.ready, 1 );
                }

                // Remember that the DOM is ready
                myBank.isReady = true;

                // If a normal DOM Ready event fired, decrement, and wait if need be
                if ( wait !== true && --myBank.readyWait > 0 ) {
                    return;
                }

                // If there are functions bound, to execute
                if ( readyList ) {
                    // Execute all of them
                    var fn,
                    i = 0,
                    ready = readyList;

                    // Reset the list of functions
                    readyList = null;

                    while ( (fn = ready[ i++ ]) ) {
                        fn.call( document, myBank );
                    }

                    // Trigger any bound ready events
                    if ( myBank.fn.trigger ) {
                        myBank( document ).trigger( "ready" ).unbind( "ready" );
                    }
                }
            }
        }

        myBank.bindReady = function() {
            if ( readyBound ) {
                return;
            }

            readyBound = true;

            // Catch cases where $(document).ready() is called after the
            // browser event has already occurred.
            if ( document.readyState === "complete" ) {
                // Handle it asynchronously to allow scripts the opportunity to delay ready
                return setTimeout( myBank.ready, 1 );
            }

            // Mozilla, Opera and webkit nightlies currently support this event
            if ( document.addEventListener ) {
                // Use the handy event callback
                document.addEventListener( "DOMContentLoaded", DOMContentLoaded, false );

                // A fallback to window.onload, that will always work
                window.addEventListener( "load", myBank.ready, false );

            // If IE event model is used
            } else if ( document.attachEvent ) {
                // ensure firing before onload,
                // maybe late but safe also for iframes
                document.attachEvent("onreadystatechange", DOMContentLoaded);

                // A fallback to window.onload, that will always work
                window.attachEvent( "onload", myBank.ready );

                // If IE and not a frame
                // continually check to see if the document is ready
                var toplevel = false;

                try {
                    toplevel = window.frameElement == null;
                } catch(e) {}

                if ( document.documentElement.doScroll && toplevel ) {
                    doScrollCheck();
                }
            }
        }

        // All myBank objects should point back to these
        rootmyBank = myBank(document);

        // Cleanup functions for the document ready method
        if ( document.addEventListener ) {
            DOMContentLoaded = function() {
                document.removeEventListener( "DOMContentLoaded", DOMContentLoaded, false );
                myBank.ready();
            };
        } else if ( document.attachEvent ) {
            DOMContentLoaded = function() {
                // Make sure body exists, at least, in case IE gets a little overzealous (ticket #5443).
                if ( document.readyState === "complete" ) {
                    document.detachEvent( "onreadystatechange", DOMContentLoaded );
                    myBank.ready();
                }
            };
        }
        // The DOM ready check for Internet Explorer
        function doScrollCheck() {
            if ( myBank.isReady ) {
                return;
            }
            try {
                // If IE is used, use the trick by Diego Perini
                // http://javascript.nwbox.com/IEContentLoaded/
                document.documentElement.doScroll("left");
            } catch(e) {
                setTimeout( doScrollCheck, 1 );
                return;
            }

            // and execute any waiting functions
            myBank.ready();
        }

        return (window.myBank = window.$ = myBank);
    })();

//for other's

})(window);
/*
(function(window, undefined){
    var myBank = function(selector, context) {
        return new myBank.fn.init(selector, context);
    },
    // Map over myBank in case of overwrite
    _myBank = window.myBank,

    // Map over the $ in case of overwrite
    _$ = window.$,
    rootmyBank,
    document = window.document,
    readyBound = false,
    readyList = [],
    DOMContentLoaded,
    // A simple way to check for HTML strings or ID strings
    // (both of which we optimize for)
    quickExpr = /^[^<]*(<[\w\W]+>)[^>]*$|^#([\w-]+)$/,
    // Match a standalone tag
    rsingleTag = /^<(\w+)\s*\/?>(?:<\/\1>)?$/,
    // Save a reference to some core methods
    toString = Object.prototype.toString,
    hasOwnProperty = Object.prototype.hasOwnProperty,
    push = Array.prototype.push,
    slice = Array.prototype.slice,
    indexOf = Array.prototype.indexOf,
    submitted = false;
    myBank.fn = myBank.prototype = {
        init: function( selector, context ) {
            var match, elem, ret, doc;
            // Handle $(""), $(null), or $(undefined)
            if ( !selector ) {
                return this;
            }
            // Handle $(DOMElement)
            if ( selector.nodeType ) {
                this.context = this[0] = selector;
                this.length = 1;
                return this;
            }
            // The body element only exists once, optimize finding it
            if ( selector === "body" && !context ) {
                this.context = document;
                this[0] = document.body;
                this.selector = "body";
                this.length = 1;
                return this;
            }
            // Handle HTML strings
            if ( typeof selector === "string" ) {
                // Are we dealing with HTML string or an ID?
                match = quickExpr.exec( selector );
                // Verify a match, and that no context was specified for #id

                if ( match && (match[1] || !context) ) {
                    // HANDLE: $(html) -> $(array)
					alert('hello...' + match[1]);
                    if ( match[1] ) {

                        doc = (context ? context.ownerDocument || context : document);
                        // If a single string is passed in and it's a single tag
                        // just do a createElement and skip the rest
                        ret = rsingleTag.exec( selector );
                        if ( ret ) {
                            if ( myBank.isPlainObject( context ) ) {
                                selector = [ document.createElement( ret[1] ) ];
                                myBank.fn.attr.call( selector, context, true );

                            } else {
                                selector = [ doc.createElement( ret[1] ) ];
                            }
                        } else {
                            ret = buildFragment( [ match[1] ], [ doc ] );
                            selector = (ret.cacheable ? ret.fragment.cloneNode(true) : ret.fragment).childNodes;
                        }
                        return myBank.merge( this, selector );
                    // HANDLE: $("#id")
                    } else {
                        elem = document.getElementById( match[2] );

                        if ( elem ) {
                            // Handle the case where IE and Opera return items
                            // by name instead of ID
							alert('hello...' + elem.id === match[2]);
                            if ( elem.id !== match[2] ) {

                                return rootmyBank.find( selector );
                            }
                            // Otherwise, we inject the element directly into the myBank object
                            this.length = 1;
                            this[0] = elem;
                        }
                        this.context = document;
                        this.selector = selector;
                        return this;
                    }
                // HANDLE: $("TAG")
                } else if ( !context && /^\w+$/.test( selector ) ) {
                    this.selector = selector;
                    this.context = document;
                    selector = document.getElementsByTagName( selector );
                    return myBank.merge( this, selector );
                // HANDLE: $(expr, $(...))
                } else if ( !context || context.jquery ) {
                    return (context || rootmyBank).find( selector );
                // HANDLE: $(expr, context)
                // (which is just equivalent to: $(context).find(expr)
                } else {
                    return myBank( context ).find( selector );
                }
            // HANDLE: $(function)
            // Shortcut for document ready
            } else if ( myBank.isFunction( selector ) ) {
                return rootmyBank.ready( selector );
            }
            if (selector.selector !== undefined) {
                this.selector = selector.selector;
                this.context = selector.context;
            }
            return myBank.makeArray( selector, this );
        },
        selector : "",
        version : 0.01,
        ready: function( fn ) {
            // Attach the listeners
            myBank.bindReady();

            // If the DOM is already ready
            if ( myBank.isReady ) {
                // Execute the function immediately
                fn.call( document, myBank );

            // Otherwise, remember the function for later
            } else if ( readyList ) {
                // Add the function to the wait list
                readyList.push( fn );
            }
            return this;
        }
    };
    myBank.fn.init.prototype = myBank.fn;
    myBank.extend = myBank.fn.extend = function() {
        // copy reference to target object
        var target = arguments[0] || {}, i = 1, length = arguments.length, deep = false, options, name, src, copy;
        // Handle a deep copy situation
        if ( typeof target === "boolean" ) {
            deep = target;
            target = arguments[1] || {};
            // skip the boolean and the target
            i = 2;
        }
        // Handle case when target is a string or something (possible in deep copy)
        if ( typeof target !== "object" && !myBank.isFunction(target) ) {
            target = {};
        }
        // extend myBank itself if only one argument is passed
        if ( length === i ) {
            target = this;
            --i;
        }
        for ( ; i < length; i++ ) {
            // Only deal with non-null/undefined values
            if ( (options = arguments[ i ]) != null ) {
                // Extend the base object
                for ( name in options ) {
                    src = target[ name ];
                    copy = options[ name ];
                    // Prevent never-ending loop
                    if ( target === copy ) {
                        continue;
                    }
                    // Recurse if we're merging object literal values or arrays
                    if ( deep && copy && ( myBank.isPlainObject(copy) || myBank.isArray(copy) ) ) {
                        var clone = src && ( myBank.isPlainObject(src) || myBank.isArray(src) ) ? src
                        : myBank.isArray(copy) ? [] : {};
                        // Never move original objects, clone them
                        target[ name ] = myBank.extend( deep, clone, copy );
                    // Don't bring in undefined values
                    } else if ( copy !== undefined ) {
                        target[ name ] = copy;
                    }
                }
            }
        }
        // Return the modified object
        return target;
    };
    myBank.extend({
        noConflict: function( deep ) {
            window.$ = _$;
            if ( deep ) {
                window.myBank = _myBank;
            }
            return myBank;
        },
        // Is the DOM ready to be used? Set to true once it occurs.
        isReady: false,
        // Handle when the DOM is ready
        ready: function() {
            // Make sure that the DOM is not already loaded
            if ( !myBank.isReady ) {
                // Make sure body exists, at least, in case IE gets a little overzealous (ticket #5443).
                if ( !document.body ) {
                    return setTimeout( myBank.ready, 13 );
                }
                // Remember that the DOM is ready
                myBank.isReady = true;
                // If there are functions bound, to execute
                if ( readyList ) {
                    // Execute all of them
                    var fn, i = 0;
                    while ( (fn = readyList[ i++ ]) ) {
                        fn.call( document, myBank );
                    }
                    // Reset the list of functions
                    readyList = null;
                }
                // Trigger any bound ready events
                if ( myBank.fn.triggerHandler ) {
                    myBank( document ).triggerHandler( "ready" );
                }
            }
        },
        bindReady: function() {
            if ( readyBound ) {
                return;
            }
            readyBound = true;
            // Catch cases where $(document).ready() is called after the
            // browser event has already occurred.
            if ( document.readyState === "complete" ) {
                return myBank.ready();
            }
            // Mozilla, Opera and webkit nightlies currently support this event
            if ( document.addEventListener ) {
                // Use the handy event callback
                document.addEventListener( "DOMContentLoaded", DOMContentLoaded, false );
                // A fallback to window.onload, that will always work
                window.addEventListener( "load", myBank.ready, false );
            // If IE event model is used
            } else if ( document.attachEvent ) {
                // ensure firing before onload,
                // maybe late but safe also for iframes
                document.attachEvent("onreadystatechange", DOMContentLoaded);
                // A fallback to window.onload, that will always work
                window.attachEvent( "onload", myBank.ready );
                // If IE and not a frame
                // continually check to see if the document is ready
                var toplevel = false;
                try {
                    toplevel = window.frameElement == null;
                } catch(e) {}
                if ( document.documentElement.doScroll && toplevel ) {
                    doScrollCheck();
                }
            }
        },
        // See test/unit/core.js for details concerning isFunction.
        // Since version 1.3, DOM methods and functions like alert
        // aren't supported. They return false on IE (#2968).
        isFunction: function( obj ) {
            return toString.call(obj) === "[object Function]";
        },
        isArray: function( obj ) {
            return toString.call(obj) === "[object Array]";
        },
        isPlainObject: function( obj ) {
            // Must be an Object.
            // Because of IE, we also have to check the presence of the constructor property.
            // Make sure that DOM nodes and window objects don't pass through, as well
            if ( !obj || toString.call(obj) !== "[object Object]" || obj.nodeType || obj.setInterval ) {
                return false;
            }
            // Not own constructor property must be Object
            if ( obj.constructor
                && !hasOwnProperty.call(obj, "constructor")
                && !hasOwnProperty.call(obj.constructor.prototype, "isPrototypeOf") ) {
                return false;
            }
            // Own properties are enumerated firstly, so to speed up,
            // if last one is own, then all properties are own.
            var key;
            for ( key in obj ) {}
            return key === undefined || hasOwnProperty.call( obj, key );
        },
        isEmptyObject: function( obj ) {
            for ( var name in obj ) {
                return false;
            }
            return true;
        },
        error: function( msg ) {
            throw msg;
        },

        noop: function() {},

        nodeName: function( elem, name ) {
            return elem.nodeName && elem.nodeName.toUpperCase() === name.toUpperCase();
        },

        // args is for internal usage only
        each: function( object, callback, args ) {
            var name, i = 0,
            length = object.length,
            isObj = length === undefined || myBank.isFunction(object);

            if ( args ) {
                if ( isObj ) {
                    for ( name in object ) {
                        if ( callback.apply( object[ name ], args ) === false ) {
                            break;
                        }
                    }
                } else {
                    for ( ; i < length; ) {
                        if ( callback.apply( object[ i++ ], args ) === false ) {
                            break;
                        }
                    }
                }

            // A special, fast, case for the most common use of each
            } else {
                if ( isObj ) {
                    for ( name in object ) {
                        if ( callback.call( object[ name ], name, object[ name ] ) === false ) {
                            break;
                        }
                    }
                } else {
                    for ( var value = object[0];
                        i < length && callback.call( value, i, value ) !== false; value = object[++i] ) {}
                }
            }

            return object;
        },

        trim: function( text ) {
            return (text || "").replace( rtrim, "" );
        },

        // results is for internal usage only
        makeArray: function( array, results ) {
            var ret = results || [];

            if ( array != null ) {
                // The window, strings (and functions) also have 'length'
                // The extra typeof function check is to prevent crashes
                // in Safari 2 (See: #3039)
                if ( array.length == null || typeof array === "string" || myBank.isFunction(array) || (typeof array !== "function" && array.setInterval) ) {
                    push.call( ret, array );
                } else {
                    myBank.merge( ret, array );
                }
            }

            return ret;
        },

        inArray: function( elem, array ) {
            if ( array.indexOf ) {
                return array.indexOf( elem );
            }

            for ( var i = 0, length = array.length; i < length; i++ ) {
                if ( array[ i ] === elem ) {
                    return i;
                }
            }

            return -1;
        },

        merge: function( first, second ) {
            var i = first.length, j = 0;

            if ( typeof second.length === "number" ) {
                for ( var l = second.length; j < l; j++ ) {
                    first[ i++ ] = second[ j ];
                }

            } else {
                while ( second[j] !== undefined ) {
                    first[ i++ ] = second[ j++ ];
                }
            }

            first.length = i;

            return first;
        },

        // A global GUID counter for objects
        guid: 1

    });

    // All myBank objects should point back to these
    rootmyBank = myBank(document);

    // Cleanup functions for the document ready method
    if ( document.addEventListener ) {
        DOMContentLoaded = function() {
            document.removeEventListener( "DOMContentLoaded", DOMContentLoaded, false );
            myBank.ready();
        };
    } else if ( document.attachEvent ) {
        DOMContentLoaded = function() {
            // Make sure body exists, at least, in case IE gets a little overzealous (ticket #5443).
            if ( document.readyState === "complete" ) {
                document.detachEvent( "onreadystatechange", DOMContentLoaded );
                myBank.ready();
            }
        };
    }
    function doScrollCheck() {
        if ( myBank.isReady ) {
            return;
        }
        try {
            // If IE is used, use the trick by Diego Perini
            // http://javascript.nwbox.com/IEContentLoaded/
            document.documentElement.doScroll("left");
        } catch( error ) {
            setTimeout( doScrollCheck, 1 );
            return;
        }
        // and execute any waiting functions
        myBank.ready();
    }

	myBank.fn.extend({
		triggerHandler: function( type, data ) {
            if ( this[0] ) {
                var event = myBank.Event( type );
                event.preventDefault();
                event.stopPropagation();
                //myBank.event.trigger( event, data, this[0] );
                return event.result;
            }
        }
	});

    var expando = "myBank" + now(), uuid = 0, windowData = {};
    function now() {
        return (new Date).getTime();
    }

    myBank.Event = function( src ) {
        // Allow instantiation without the 'new' keyword
        if ( !this.preventDefault ) {
            return new myBank.Event( src );
        }

        // Event object
        if ( src && src.type ) {
            this.originalEvent = src;
            this.type = src.type;
        // Event type
        } else {
            this.type = src;
        }

        // timeStamp is buggy for some events on Firefox(#3843)
        // So we won't rely on the native value
        this.timeStamp = now();

        // Mark it as fixed
        this[ expando ] = true;
    };

    function returnFalse() {
        return false;
    }
    function returnTrue() {
        return true;
    }

    // myBank.Event is based on DOM3 Events as specified by the ECMAScript Language Binding
    // http://www.w3.org/TR/2003/WD-DOM-Level-3-Events-20030331/ecma-script-binding.html
    myBank.Event.prototype = {
        preventDefault: function() {
            this.isDefaultPrevented = returnTrue;

            var e = this.originalEvent;
            if ( !e ) {
                return;
            }

            // if preventDefault exists run it on the original event
            if ( e.preventDefault ) {
                e.preventDefault();
            }
            // otherwise set the returnValue property of the original event to false (IE)
            e.returnValue = false;
        },
        stopPropagation: function() {
            this.isPropagationStopped = returnTrue;

            var e = this.originalEvent;
            if ( !e ) {
                return;
            }
            // if stopPropagation exists run it on the original event
            if ( e.stopPropagation ) {
                e.stopPropagation();
            }
            // otherwise set the cancelBubble property of the original event to true (IE)
            e.cancelBubble = true;
        },
        stopImmediatePropagation: function() {
            this.isImmediatePropagationStopped = returnTrue;
            this.stopPropagation();
        },
        isDefaultPrevented: returnFalse,
        isPropagationStopped: returnFalse,
        isImmediatePropagationStopped: returnFalse
    };


    rootmyBank = myBank(document);
    window.myBank = window.$ = myBank;
})(window);
*/
var goPrincipalAmount = (function (){
    //private static
    var charCodeCounter = 0;
    var decimalCounter = 0;
    //private static method
    /*var isValid = function(value) {
			var val = value.toString();
			var regex = /^\d*\.?\d*$/g;
			if(!val.match(regex)){
				return false;
			}
			return true;
		}*/
    //constructor
    return function(){
        /* place for constructor*/
        //method
        this.doCheck = function(e) {
            var evt = window.event || e;
            var element = evt.srcElement || evt.target;
            var charCode = evt.keyCode;
            var dotCounter = 0;
            if(charCode > 31 && ((charCode < 48 && charCode != 46) || charCode > 57)) {
                status = 'This field accepts numbers only.';
                evt.returnValue = false;
            }
            var value = element.value;
            var idx = value.indexOf('.');
            while(idx != -1) {
                dotCounter++;
                idx = value.indexOf('.', idx + 1);
            }
            if(dotCounter >= 1){
                if(charCode == 46){
                    status = 'Sorry more than one period not allowed.';
                    evt.returnValue = false;
                } else {
                    var decimalIndex = value.indexOf('.');
                    decimalIndex++;
                    var subst = value.substring(decimalIndex);
                    decimalCounter = subst.length;
                    if(decimalCounter >= 2){
                        status = '';
                        evt.returnValue = false;
                    }
                }
            }
            charCodeCounter = value.length;
        //status = '';
        }
    /* place for constructor*/
    };
})();

var MyBank = {};
MyBank.SubmitionProgress = function(){
    var submitted = false;
    this.doSubmit = function(){
        if (!submitted) {
            disableAllLinks();

            var f = document.getElementById('loanRepaymentForm');
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
            var ds = div.style;
            ds.backgroundColor = '#1FF';
            ds.width = winWidth + 'px';
            ds.heigth = winHeight + 'px';
            ds.fontSize = '32px';
            ds.position = 'absolute';
            ds.left = Left;
            ds.top = Top;
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

var SubmitionProgresss = {
    submitted : false,
    doSubmit : function(){
        if (!this.submitted) {
            disableAllLinks();

            var f = document.getElementById('loanRepaymentForm');
            var winHeight = 100;
            var winWidth = 250;
            var Top = ((getInsideWindowHeight()/2) - (winHeight / 2));
            var Left = ((getInsideWindowWidth()/2) - (winWidth / 2));

            this.submitted = true;
            var div = document.createElement('div');
            var para = document.createElement('p');
            para.textContent = 'Please Wait...';
            para.innerText = 'Please Wait...';
            div.appendChild(para);
            div.style.backgroundColor = '#1FF';
            div.style.width = winWidth + 'px';
            div.style.heigth = winHeight + 'px';
            div.style.fontSize = '32px';
            div.style.position = 'absolute';
            div.style.left = Left;
            div.style.top = Top;
            var firstNode = f.firstChild;
            f.insertBefore(div, firstNode);
            return true;
        } else {
            return false;
        }
    },
    disableAllLinks : function(){
        var allLinks = document.links;
        for(var i = 0; i < allLinks.length; i++){
            var aLink = allLinks.item(i);
            aLink.onclick = null;
        }
    },
    getInsideWindowWidth : function() {
        if (window.innerWidth) {
            return window.innerWidth;
        } else if (document.body.scrollWidth) {
            // measure the html element's clientHeight
            return document.body.scrollWidth;
        } else if (document.width) {
            return document.width;
        }
        return 0;
    },
    getInsideWindowHeight : function() {
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
}

function test(){
    document.createElement('');
    
}

function domReady(fnRef) {
    // This part will work in Firefox, Mozilla variants, Newer Opera, and Newer Webkit
    if (document.addEventListener) {
        document.addEventListener("DOMContentLoaded", fnRef, false);
    } else {
        if (document.documentElement.doScroll) {
            try {
                document.documentElement.doScroll("left");
            } catch(e) {
                setTimeout(function() {
                    domReady(fnRef)
                }, 13);
                return;
            }
        } else {
            // Nothing here will work, let's just bind to onLoad
            window.attachEvent("onload", fnRef);
            return;
        }
        // Our code goes here!
        fnRef.apply();
    }
}

var tabClass = function(){
    var divContainer = document.getElementById('tabContainer');
    var myvar = divContainer.childNodes;
    document.createElement('input');
    var image = new Image();
    Event.initKeyEvent("keypress", true, true, null, false, false, false, false, 13, 0);
}

/*var MyBank = function (newFormName){
  var formName;

  function _settingName(url){
    if (url == undefined || typeof url != 'string') {
      return false;
    }
    var newURL = url.substr(0, url.lastIndexOf('.'));
    var winName = newURL.charAt(0).toUpperCase()+newURL.substr(1);
    return true;
  }

  this.getFormName = function() {
    return formName;
  };
  this.setFormName = function(newFormName) {
    if (newFormName == undefined || typeof newFormName != 'string') {
      throw new Error('MyBank : Form Name not valid');
    }
    formName = newFormName;
  }
  this.openWindow380 = function(url){
    //winURL = url; // for normal window open
    winURL = ""; // for parameter passing
    winName = _settingName(url);
    winHeight = 380;
    winWidth = screen.availWidth - 15;
    winTop = ((screen.availHeight/2) - (winHeight / 6));
    winLeft = 0;
    winOpen = "";
    if(winOpen){
      if(winOpen.closed){
        winOpen = window.open(winURL, winName, "top=" + winTop + ",left=" + winLeft + ",width=" + winWidth + ",height=" + 				winHeight + ",resizable=no,toolbar=no,scrollbars=yes,location=no,status=no,menubar=no")
      } else {
        winOpen.close();
        winOpen = window.open(winURL, winName, "top=" + winTop + ",left=" + winLeft + ",width=" + winWidth + ",height=" + winHeight + ",resizable=no,toolbar=no,scrollbars=yes,location=no,status=no,menubar=no")
      }
    } else {
      winOpen = window.open(winURL, winName, "top=" + winTop + ",left=" + winLeft + ",width=" + winWidth + ",height=" + winHeight + ",resizable=no,toolbar=no,scrollbars=yes,location=no,status=no,menubar=no")
    }
    winOpen.focus();
  //for parameter passing
		document.testForm.textBox.value = "AA";
		document.testForm.action = winURL;
		document.testForm.target = winName;
		document.testForm.submit();
  }
}

MyBank.prototype = {
  goNamedField : function(evt, fieldName){
    evt = (evt) ? evt : (window.event) ? window.event : "";
    //var element = evt.srcElement || evt.target;
    if (evt.keyCode == 13) {
      document.getElementById(fieldName).focus();
    }
  },
  openWindow600X380 : function(url){
    winURL ="customerInfoDepositAccountOpeningRecurring.do?";
    winName = "CustomerInfoDepositAccountOpeningRecurring";
    winHeight = 380;
    winWidth = screen.availWidth - 15;
    winTop = ((screen.availHeight/2) - (winHeight / 6));
    winLeft = 0;
    winOpen = "";
    if(winOpen){
      if(winOpen.closed){
        winOpen = window.open(winURL, winName, "top=" + winTop + ",left=" + winLeft + ",width=" + winWidth + ",height=" + 				winHeight + ",resizable=no,toolbar=no,scrollbars=yes,location=no,status=no,menubar=no")
      } else {
        winOpen.close();
        winOpen = window.open(winURL, winName, "top=" + winTop + ",left=" + winLeft + ",width=" + winWidth + ",height=" + winHeight + ",resizable=no,toolbar=no,scrollbars=yes,location=no,status=no,menubar=no")
      }
    } else {
      winOpen = window.open(winURL, winName, "top=" + winTop + ",left=" + winLeft + ",width=" + winWidth + ",height=" + winHeight + ",resizable=no,toolbar=no,scrollbars=yes,location=no,status=no,menubar=no")
    }
    winOpen.focus();
  }
}*/
function $() {
    var elements = [];
    for (var i = 0, len = arguments.length; i < len; ++i) {
        var element = arguments[i];
        if (typeof element === 'string') {
            element = document.getElementById(element);
        }
        if (arguments.length === 1) {
            return element;
        }
        elements.push(element);
    }
    return elements;
}

MyBank = (function(){
    return {
        addEvent : function(el, type, fn){
            if (window.addEventListener) {
                el.addEventListener(type, fn, false);
            }
            else if (window.attachEvent) {
                el.attachEvent('on' + type, fn);
            }
            else {
                el['on' + type] = fn;
            }
        },
        goAction : function(evt, formName, handler){
            evt = evt || window.event;
            var key = evt.keyCode || evt.charCode;
            if(key == 13){
                if(typeof formName === 'string'){
                    var formElem = $(formName);
                    if(handler instanceof Object){
                        var fn = handler.method;
                        if(typeof fn === 'function'){
                            //fn = this[fn];
                            if(handler.parameters instanceof Array){
                            //fn(handler.parameters);
                            //alert(fn(handler.parameters));
                            //return;
                            } else if(handler.parameters instanceof Object){
                                //alert('bb');
                                var elements = handler.parameters.elements;
                                var messages = handler.parameters.messages;
                                fn(formElem, elements, messages);
                            }
                        }
                    }
                }
            }
        },
        goAction1 : function(evt, formName, fn){
            evt = evt || window.event;
            var key = evt.keyCode || evt.charCode;
            if(key == 13){
                if(typeof formName === 'string'){
                    var formElem = $(formName);
                    if(typeof fn === 'function'){
                        fn(formElem);
                    }
                }
            }
        },
        goAction2 : function(evt, elemName){
            evt = evt || window.event;
            var key = evt.keyCode || evt.charCode;
            if(key == 13){
                if(typeof elemName === 'string'){
                    var elem = $(elemName);
                    //alert(elem.nodeName === 'FORM');
                    if(elem.nodeName !== 'FORM'){
                        elem.focus();
                    } else {
                        doExecute(elem);
                    }
                } else {
            //alert('HH');
            }
            }
        },
        goNamedField : function(evt, fieldName){
            if(window.event){
                evt = window.event;
            }
            if(evt.keyCode == 13){
                document.getElementById(fieldName).focus();
            }
        },
        doAction : function(f, elements, messages){
            for(var i = 0; i < elements.length; i++){
                if(f.elements[i].value == ''){
                    alert(messages[i]);
                    f.elements[i].focus();
                    return;
                }
            }
            f.action = '#';
            f.submit();
        }
    }
})();
MyBank.Form = function(){
    // TODO
    };
MyBank.Util = (function(){
    return {
        trim : function(value){
            var regEx = /^\s+|\s+$/g;
            return value.toString().replace(regEx,'');
        },
        isEmpty : function(value){
            var regEx = /.+/;
            if(!regEx.test(value.toString())){
                return true;
            }
            return false;
        },
        isDigit : function(value){
            value = MyBank.Util.trim(value.toString());
            var regEx = /^[\d]$/;
            if(regEx.test(value)){
                return true;
            }
            return false;
        },
        isInteger : function(value){
            var regEx;
            value = MyBank.Util.trim(value.toString());
            (arguments[1] == undefined) ? (regEx = '^\\d+$') : (regEx = '^\\d{'+arguments[1]+'}$');
            regEx = new RegExp(regEx);
            if(regEx.test(value)){
                return true;
            }
            return false;
        }
    };
})();
MyBank.Util.Event = {
    addEvent : function(el, type, fn){
        if (window.addEventListener) {
            el.addEventListener(type, fn, false);
        }
        else if (window.attachEvent) {
            el.attachEvent('on' + type, fn);
        }
        else {
            el['on' + type] = fn;
        }
    },
    getEvent : function(evt){
        return evt || window.event;
    },
    getTarget : function(evt){
        return evt.target || evt.srcElement;
    },
    stopPropagation : function(evt){
        if(evt.stopPropagation){
            evt.stopPropagation();
        } else {
            evt.cancelBubble = true;
        }
    },
    preventDefault : function(evt){
        if(evt.preventDefault){
            evt.preventDefault();
        } else {
            evt.returnValue = false;
        }
    },
    stopEvent : function(evt){
        this.stopPropagation(evt);
        this.preventDefault(evt);
    },
    domReady : function(fnRef){
        // This part will work in Firefox, Mozilla variants, Newer Opera, and Newer Webkit
        if (document.addEventListener) {
            document.addEventListener("DOMContentLoaded", fnRef, false);
        } else {
            if (document.documentElement.doScroll) {
                try {
                    document.documentElement.doScroll("left");
                } catch(e) {
                    setTimeout(function() {
                        domReady(fnRef)
                    }, 13);
                    return;
                }
            } else {
                // Nothing here will work, let's just bind to onLoad
                window.attachEvent("onload", fnRef);
                return;
            }
            // Our code goes here!
            fnRef.apply();
        }
    }
}

var Book = (function() {
    // Private static attributes.
    var numOfBooks = 0;
    // Private static method.
    function checkIsbn(isbn) {
        return true;
    }
    // Return the constructor.
    return function(newIsbn, newTitle, newAuthor) { // implements Publication
        // Private attributes.
        var isbn, title, author;
        // Privileged methods.
        this.getIsbn = function() {
            return isbn;
        };
        this.setIsbn = function(newIsbn) {
            if(!checkIsbn(newIsbn)) throw new Error('Book: Invalid ISBN.');
            isbn = newIsbn;
        };
        this.getTitle = function() {
            return title;
        };
        this.setTitle = function(newTitle) {
            title = newTitle || 'No title specified';
        };
        this.getAuthor = function() {
            return author;
        };
        this.setAuthor = function(newAuthor) {
            author = newAuthor || 'No author specified';
        };
        // Constructor code.
        numOfBooks++; // Keep track of how many Books have been instantiated
        // with the private static attribute.
        if(numOfBooks > 50) throw new Error('Book: Only 50 instances of Book can be created.');
        this.setIsbn(newIsbn);
        this.setTitle(newTitle);
        this.setAuthor(newAuthor);
    }
})();

// Public static method.
Book.convertToTitleCase = function(inputString) {
    alert(inputString);
};
// Public, non-privileged methods.
Book.prototype = {
    display: function() {
    //alert(this.getIsbn());
    }
};