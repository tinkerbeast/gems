/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function TransactionUnit (element) {
    this.element = element;    
    if(this.element==null) {
        throw error;
    } else {
        this.element.children("button").click(this.onClose);
    }    
};
            
TransactionUnit.prototype.onClose  = function() {
    // NOTE: `this` here will refere to the button element whre the function is binded
    var fieldset = this.parentNode;
    fieldset.parentNode.removeChild(fieldset); // WARNING - DOM method used    
};
            
TransactionUnit.prototype.getHtmlElement = function() {
    return this.element;
};


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
        this.fieldElement = jQuery(this.fieldHtml);
        this.fieldElement.children("select").append($(userList).children());      
    },
    
    //
    produceUnit: function() {
        if(this.fieldElement==null) {
            return null;
        } else {
            return new TransactionUnit(this.fieldElement.clone());
        }
    }
};
