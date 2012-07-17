/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var TransactionUnitFactory = {
    //
    fieldHtml: "<fieldset class=\"gems-partyInformation\">     \n\
                    <select required=\"true\"></select>        \n\
                    <input type=\"number\" required=\"true\"/> \n\
                    <button class=\"close\">&times;</button>   \n\
                </fieldset>",
    
    //
    fieldElement: null,
    
    //
    initialise: function(userList) {
      this.fieldElement = jQuery(fieldHtml);
      this.fieldElement.children("select").append($(userList).children());
      this.fieldElement.children("button").click(this.onClose);
    },
    
    //
    onClose: function() {
        // NOTE: `this` here will refere to the button element whre the function is binded
        var fieldset = this.parentNode;
        fieldset.parentNode.removeChild(fieldset); // WARNING - DOM method used 
    },
    
    //
    produceUnit: function() {
        if(fieldElement==null) {
            return null;
        } else {
            return fieldElement.clone();
        }
    }
    
    
};


