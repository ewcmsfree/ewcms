/*!
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
 
var GroupEdit = function(urls){
    this._urls = urls;
}

GroupEdit.prototype._queryNew = function(){
    var newGroupNames = [];
    $.each($("input[name=newGroupNames]"),function(index,input){
       newGroupNames.push($(input).val());
    });
    parent.queryNews(newGroupNames);
}

GroupEdit.prototype.init = function(opts){
 
    var urls = this._urls;
    
    if(opts.addSaveState){
        $("#button-save").css('display','none');
        $("#button-new").css('display','');
        $('input[name=name]').attr('readonly',true);
        $('textarea').attr('readonly',true);
        this._queryNew();
    }

    $(".easyui-tabs").tabs({
        onSelect:function(title){  
            if(title == "权限/用户" && !$('#editifr-id').attr('src')){
                var url = urls.detailUrl + '?showTitle=false&name=' + opts.name;
                $('#editifr-id').attr('src',url);
            }
        }  
    });
    
  $('input[name=name]').bind('focusout', function(){
      if($(this).attr('readonly')){
          return;
      }
      var url = urls.hasNameUrl + '?name='+$(this).val();
      $.getJSON(url,function(data){
          if(data.exist){
              $('#name-td ul').remove();
              $('<ul>').appendTo('#name-td');
              $('<li>用户组名已经存在</li>').appendTo('#name-td ul');
          }else{
              $('#name-td ul').remove();
          }
      });
    });
        
    $("#button-new").bind('click',function(){
        $(".easyui-tabs").tabs("close","权限/用户");
        $('input[name=name]').val('');
        $('textarea').val('');
        $('input[name=name]').attr('readonly',false);
        $('textarea').attr('readonly',false);
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
}