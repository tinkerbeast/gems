/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function TransactionUnit (element, container) {
    this.element = element;
    this.valid = true;
    this.value = 0;    
    this.container = container;
    
    if(this.element==null) {
        throw error;
    } else {
        this.element.children("button").bind("click", {
            container: container, 
            self: this
        }, this.onClose);
        
        this.element.children("input").bind("change", {
            container: container, 
            self: this
        }, this.onChange);        
    }    
};
            
TransactionUnit.prototype.onClose  = function(event) {
    
    event.data.container.onRemove(event.data.self);
    
    // NOTE: `this` here will refere to the button element whre the function is binded
    var fieldset = this.parentNode;            // WARNING - DOM method used    
    fieldset.parentNode.removeChild(fieldset); // WARNING - DOM method used    
};

TransactionUnit.prototype.onChange = function(event) {
    event.data.container.onUpdate(event.data.self);
};

TransactionUnit.prototype.isValid = function() {
    // TODO use val() method
    var val = this.element.children("input").attr("value");    
    return (val.search(/^\d+(\.\d+)?$/) != -1);
};

TransactionUnit.prototype.getValue = function() {
    // TODO use val() method
    return this.element.children("input").attr("value");
};

TransactionUnit.prototype.getHtmlElement = function() {
    return this.element;
};




var TransactionUnitFactory = {
    //
    fieldHtml: "<fieldset class=\"gems-transactionUnit\">     \n\
                    <select required></select>        \n\
                    <input type=\"text\" value=\"0\" pattern=\"[0-9]+(\\.[0-9]+)?\"  required/> \n\
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
    produceUnit: function(container) {
        if(this.fieldElement==null) {
            return null;
        } else {
            return new TransactionUnit(this.fieldElement.clone(), container);
        }
    }
};
