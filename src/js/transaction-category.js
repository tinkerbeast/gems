/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function TransactionCategory (element) {
    this.element = $(element);
    this.isValid = false;
    this.sumVal = 0;
    this.list = new LinkedList.Circular();
    
    
    if(this.element == null) {
        throw error;
    } else {
        this.element.find(".gems-btn-add").bind("click", {
            self: this
        }, this.onAdd);
    }
};
            
TransactionCategory.prototype.onAdd = function(event) {
    
    var self = event.data.self;
    var tranUnit = TransactionUnitFactory.produceUnit(self);
    
    self.list.append(tranUnit);
    
    // this == div.gems-transactionCategory fieldset legend button.gems-btn-add
    var fieldset = this.parentNode.parentNode; // WARNING - DOM method used    
    $(fieldset).append(tranUnit.getHtmlElement());
};

TransactionCategory.prototype.onRemove  = function(removedObject) {    
    this.list.remove(removedObject);
    this.onUpdate(removedObject);
};

TransactionCategory.prototype.onUpdate  = function(updateObject) {
    
    var node = this.list.first;
    var sum = 0.0;
    for (var i = 0; i < this.list.length; i++) {
        if(node.isValid()) {
            sum += node.getValue();
        } else {
            sum = -1;
            break;
        }
        
        node = node.next;
    }
    
    if (sum > 0) {
        this.isValid = true;
        this.sumVal = sum;
        this.element.find("output").text("(" + sum + ")");
    } else {
        this.isValid = false;
        this.sumVal = 0;
        this.element.find("output").text("");
    }
};

TransactionCategory.prototype.getCommitData  = function() {
    
    var node = this.list.first;
    
    for (var i = 0; i < this.list.length; i++) {
      
    }   
};

