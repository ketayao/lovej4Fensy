/*******************************************************************************
* Insert Quote Plugin for KindEditor 4.1.2
*
* @author tsl0922 <tsl0922@gmail.com>
* @site http://my.oschina.net/tsl0922
*******************************************************************************/

KindEditor.plugin('quote', function(K) {
	var self = this, name = 'quote';
	self.clickToolbar(name, function() {
		var lang = self.lang(name + '.'),
			html = ['<div style="padding:10px 20px;">',
				'<div class="ke-dialog-row">',
				'请输入要引用的内容',
				'</div>',
				'<textarea class="ke-textarea" style="width:500px;height:200px;font-size:9pt;font-family:Courier New,Arial"></textarea>',
				'</div>'].join(''),
			dialog = self.createDialog({
				name : name,
				width : 550,
				title : self.lang(name),
				body : html,
				yesBtn : {
					name : self.lang('yes'),
					click : function(e) {
						var source = textarea.val();
						if(source == ''){
							alert(self.lang('ivalid_quote'));
							return false;
						}
						var html = '<blockquote>' + K.escape(source) + '</blockquote>';
						self.insertHtml(html).hideDialog().focus();
					}
				}
			}),
			textarea = K('textarea', dialog.div);
		textarea[0].focus();
	});
});
