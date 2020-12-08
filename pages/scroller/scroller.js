var QScroller = new Class({
	options: {
		slides: 'qslide',
		direction: 'h',
		duration: 3000,
		auto: false,
		delay: 1000,
		transition: Fx.Transitions.linear
	},
	initialize: function(wrapper,options) {
		this.setOptions(options);
		this.wrapper = $(wrapper);
		this.wrapper.setStyles({
			position: 'relative',
			overflow: 'hidden'
		});
		this.wrapper.addEvent('mouseenter', this.fireEvent.pass('onMouseEnter',this));
		this.wrapper.addEvent('mouseleave', this.fireEvent.pass('onMouseLeave',this));
				
		this.slideOut = new Element('div').setStyles({
			position: 'absolute',
			overflow: 'hidden',
			top: 0,
			left: 0,
			width: this.wrapper.getStyle('width'),
			height: this.wrapper.getStyle('height')
		}).injectInside(this.wrapper);

		this.slideIn = this.slideOut.clone();
		this.slideIn.injectInside(this.wrapper);
				
		this.slides = $$('.'+this.options.slides);
		
		if($defined(this.options.buttons)) {
			if($defined(this.options.buttons.next)) {
				$(this.options.buttons.next).addEvent('click', this.next.bind(this));
			}
			if($defined(this.options.buttons.prev)) {
				$(this.options.buttons.prev).addEvent('click', this.prev.bind(this));
			}	
			if($defined(this.options.buttons.play)) {
				$(this.options.buttons.play).addEvent('click', this.play.bind(this));
			}	
			if($defined(this.options.buttons.stop)) {
				$(this.options.buttons.stop).addEvent('click', this.stop.bind(this));
			}	
		}
		this.auto = this.options.auto;
		this.idxSlide = 0;
		this.step = 0;
		this.isFirst = true;
	},
	load: function() {
		if(!this.isFirst) {
			this.idxSlide += this.step;
			if(this.idxSlide > this.slides.length-1) {
				this.idxSlide = 0;
			} else if(this.idxSlide < 0) {
				this.idxSlide = this.slides.length-1;
			}
		}
		this.curSlide = this.slides[this.idxSlide].clone(false,false);
		this.curSlide.set('html', this.slides[this.idxSlide].get('html'));
		this.show();
	},
	show: function() {
		var slide = this.slideIn.getElement('div');
		if(slide) {
			this.curSlide.replaces(slide);
		} else {
			this.curSlide.inject(this.slideIn);
		}
		this.doEffect();
	},
	doEffect: function() {
		this.fxOn = true;
		var d = this.isFirst ? 0:this.options.duration;
		var t = this.options.transition;
		
		this.slideIn.set('morph',{duration:d, transition: t});
		var inX = 0;
		var inY = 0;
		var outX = 0;
		var outY = 0;
		var ww = this.wrapper.getStyle('width').toInt();
		var wh = this.wrapper.getStyle('height').toInt();
		if(this.step > 0) {
			if(this.options.direction == 'h') {
				inX = -ww;
				outX = ww;
			} else {
				inY = -wh;
				outY = wh;
			}
		} else {
			if(this.options.direction == 'h') {
				inX = ww;
				outX = -ww;
			} else {
				inY =  wh;
				outY = -wh;
			}
		}
		if(this.isFirst) {
			if(this.auto) {
				this.step = 1;
			}
			this.isFirst = false;
		}
		
		this.slideOut.set('morph', {duration:d, transition: t});
		
		this.slideIn.morph({
			top: [inY, 0],
			left: [inX, 0],
			opacity: [1, 1]
		});
		this.slideOut.morph({
			top: [0, outY],
			left: [0, outX]
		});
		this.fxEnd.delay(d + 75, this);
	},
	fxEnd: function() {
		this.fxOn = false;
		this.swapSlides();
		if(this.auto) {
			$clear(this.timer);
			this.timer = this.load.delay(this.options.delay, this);
		}
	},
	stop: function(){
		$clear(this.timer);
		this.auto = false;
	},
	play: function() {
		if(!this.auto ) {
			$clear(this.timer);
			this.auto=true;
			this.step = 1;
			if(!this.fxOn) {
				this.load();
			}
		}
	},
	next: function() {
		this.stop();
		if(this.fxOn) { return; }
		this.step = 1;
		this.load();
	},
	prev: function() {
		this.stop();
		if(this.fxOn) { return; }
		this.step = -1;
		this.load()
	},
	swapSlides: function() {
		this.slideOut.setStyles({
			zIndex: 0,
			opacity: 0
		});
		var t = this.slideOut;
		this.slideOut =this.slideIn;
		this.slideIn = t;
	}
});
QScroller.implement(new Options, new Events);