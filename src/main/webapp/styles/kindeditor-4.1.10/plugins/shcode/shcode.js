/*******************************************************************************
* Insert Code Plugin for KindEditor 4.1.2
* (Syntax Highlighter)
*
* @author tsl0922 <tsl0922@gmail.com>
* @site http://my.oschina.net/tsl0922
*******************************************************************************/

KindEditor.plugin('shcode', function(K) {
	var self = this, name = 'shcode';
	self.clickToolbar(name, function() {
		var html = ['<div style="padding:10px 20px;">',
				'<div class="ke-dialog-row">',
				'<select class="ke-code-type">',
				'<option value="">[选择编程语言]</option>',    		
            	'<option value="java">Java</option>',
        		'<option value="cpp">C/C++/Objective-C</option>',
        		'<option value="c#">C#</option>',
        		'<option value="js">JavaScript</option>',
        		'<option value="php">PHP</option>',
        		'<option value="perl">Perl</option>',
        		'<option value="python">Python</option>',
        		'<option value="ruby">Ruby</option>',
        		'<option value="html">HTML</option>',
        		'<option value="xml">XML</option>',
        		'<option value="css">CSS</option>',
        		'<option value="vb">ASP/Basic</option>',
        		'<option value="pascal">Delphi/Pascal</option>',
        		'<option value="scala">Scala</option>',
        		'<option value="groovy">Groovy</option>',
        		'<option value="lua">Lua</option>',
        		'<option value="sql">SQL</option>',
        		'<option value="cpp">Google Go</option>',
        		'<option value="as3">Flash/ActionScript/Flex</option>',
        		'<option value="xml">WPF/SliverLight</option>',
        		'<option value="shell">Shell/批处理</option>',
				'</select>',
				' 以便系统进行正确的语法着色',
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
						var lang = K('.ke-code-type', dialog.div).val(),
							source = textarea.val();
						if(lang == ''){
							alert(self.lang('invalid_lang'));
							return false;
						}
						if(source == ''){
							alert(self.lang('invalid_source'));
							return false;
						}
						var cls ='brush:' + lang + '; toolbar: true; auto-links: false;';
						var html = '<pre class="' + cls + '">\n' + K.escape(source) + '</pre>';
						self.insertHtml(html).hideDialog().focus();
					}
				}
			}),
			textarea = K('textarea', dialog.div);
		textarea[0].focus();
	});
});
