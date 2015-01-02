	jQuery(document).ready(function() {
		App.init();
		TableAdvanced.init();
		FormComponents.init();
	});
(function($) {
			$.icheck = {
				init : function() {
					var _this = this;
					_this._checkbox = "checkbox";
					_this._checked = "checked";
					_this._hover = "hover";
					_this._arrtype = [ _this._checkbox ];
					_this._mobile = /ipad|iphone|ipod|android|blackberry|windows phone|opera mini|silk/i
							.test(navigator.userAgent);

					$.each(_this._arrtype, function(k, v) {
						_this.click(v);

						if (!_this._mobile) {
							_this.mouseover(v);
							_this.mouseout(v);
						}
					});
				},

				click : function(elem) {
					var _this = this;
					elem = "." + elem;
					$(document).on(
							"click",
							elem,
							function() {
								var $this = $(this), _ins = $this.find("ins");
								if (!(_ins.hasClass(_this._disabled) || _ins
										.hasClass(_this._enable))) {
									if (!!_ins.hasClass(_this._checked)) {
										_ins.removeClass(_this._checked)
												.addClass(_this._hover);
										$(_ins).find("input").attr("ischecked",
												"false");
									} else {
										if (/radio/ig.test(elem)) {
											var _name = $this.attr("name");
											$(elem + "[name=" + _name + "]")
													.find("ins").removeClass(
															_this._checked);
										}
										$(elem).find("ins").removeClass(
												_this._hover);
										_ins.addClass(_this._checked);

										$(_ins).find("input").attr("ischecked",
												"true");
									}
								}
							});
				},
				mouseover : function(elem) {
					var _this = this;
					elem = "." + elem;
					$(document).on(
							"mouseover",
							elem,
							function() {
								var $this = $(this);
								var _ins = $this.find("ins");
								if (!(_ins.hasClass(_this._disabled)
										|| _ins.hasClass(_this._enable) || _ins
										.hasClass(_this._checked))) {
									_ins.addClass(_this._hover);
									$this.css("cursor", "pointer");
								} else {
									$this.css("cursor", "default");
								}
							});
				},
				mouseout : function(elem) {
					var _this = this;
					elem = "." + elem;
					$(document).on("mouseout", elem, function() {
						$(elem).find("ins").removeClass(_this._hover);
					});
				}
			};
			$.icheck.init();
		})(jQuery);