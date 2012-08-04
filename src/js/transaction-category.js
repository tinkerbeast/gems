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
        this.sumVal = 0.0;
        this.element.find("output").text("");
    }
};

TransactionCategory.prototype.getData  = function() {
    
    var userList = new Array();
    var amountList = new Array();
    
    var node = this.list.first;
    for (var i = 0; i < this.list.length; i++) {
        var data = node.getData();
        userList.push(data.user);
        amountList.push(data.amount);
        node = node.next;
    }   
    
    return {
        "userList":  userList, 
        "amountList": amountList
    };
};

TransactionCategory.prototype.setSplitValue = function(amount) {
    // TODO validate amount
    // TODO is parseFloat really necessary?
    var splitVal = amount/parseFloat(this.list.length);
    var node = this.list.first;
    for (var i = 0; i < this.list.length; i++) {
        var data = node.setValue(splitVal);
        node = node.next;
    }
    this.onUpdate(null);
};