/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var UserEdit = function(urls){
    this._urls = urls;
}

UserEdit.prototype._queryNew = function(){
    var newGroupNames = [];
    $.each($("input[name=newUsernames]"),function(index,input){
       newGroupNames.push($(input).val());
    });
    parent.queryNews(newGroupNames);
}

UserEdit.prototype.init = function(opts){
 
    var urls = this._urls;
    
    if(opts.addSaveState){
        $("#button-save").css('display','none');
        $("#button-new").css('display','');
        $('input[type=text]').attr('readonly',true);
        this._queryNew();
    }

    $(".easyui-tabs").tabs({
        onSelect:function(title){  
            if(title == "权限/用户组" && !$('#editifr-id').attr('src')){
                var url = urls.detailUrl + '?showTitle=false&username=' + opts.username;
                $('#editifr-id').attr('src',url);
            }
        }  
    });
    
  $('#usernameid').bind('focusout', function(){
      if($(this).attr('readonly')){
          return;
      }
      var url = urls.hasNameUrl + '?username='+$(this).val();
      $.getJSON(url,function(data){
          if(data.exist){
              $('#name-td ul').remove();
              $('<ul>').appendTo('#name-td');
              $('<li>用户名已经存在</li>').appendTo('#name-td ul');
          }else{
              $('#name-td ul').remove();
          }
      });
    });
        
    $("#button-new").bind('click',function(){
        $(".easyui-tabs").tabs("close","权限/用户组");
        $('input[type=text]').val('');
        $('input[type=text]').attr('readonly',false);
        $('#name-td ul').remove();
        $("#button-save").css('display','');
        $(this).css('display','none');
  });
    
    $("#button-save").bind('click',function(){
        $('form').submit();
    });
    
    $('#button-cancel').bind('click',function(){
        parent.closeEditWindow();
    });
    
    $('#button-default-password').bind('mouseover',function(){
        $("#default-password").css('display','');
    }).bind('mouseout',function(){
        $("#default-password").css('display','none');
    });
}
