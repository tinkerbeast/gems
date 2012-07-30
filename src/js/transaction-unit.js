/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 * Abbreviations:
 * DOM - Document object model
 * HTML - Hyper text markup language
 * JS - JavaScript
 * UI - User interface
 */
 
/**
 * @fileOverview
 * Support classes for `src/html/transaction` page.
 * The `src/html/transaction` page requires JS support for rendering and 
 * functioning of certain UI elements. This file contains all classes and 
 * objects necessary for that.
 *
 * @requires jQuery
 */

// TODO documentation for throw error 
// TODO @class is currently discouraged
/**
 * @public
 * @class
 * TransactionUnit class.
 * This class provides the `{user, amount}` UI-components in 
 * `src/html/transaction` support for interactivity, validation and form-data 
 * through JS.
 * 
 * @public
 * @constructor
 * Constructor.
 * The constructor initializes data elements for form-data and validation. It 
 * binds a validation function to the UI-component. It also provides a 
 * close-button interactivity to the UI-component.
 *
 * @param  {HTMLFieldSetElement} element The root element of the UI-component.
 * @param  {HTMLFieldSetElement} container The parent container for the UI-component.
 */
function TransactionUnit (element, container) {
    // TODO validation of input types
    this.element = jQuery(element);
    this.valid = true;
    this.value = 0;    
    this.container = container;
    
    if(this.element==null) {
        // TODO error handling
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

/**
 * @private
 * Action for the close button in the UI-component.
 * The close action removes the current HTML element from the DOM. It also
 * notifies the parent container that `TransactionUnit` instance unit has been
 * removed.
 * /attention Since the function will serve as an event handler, `this` will
 * refer to the button element from  where the function is invoked.
 * 
 * @param {jQuery.Event} event Event oject corresponding to the invokation event.
 * By convention `event.data.self` refers to `TransactionUnit` instance
 * 
 * @return undefined
 */            
TransactionUnit.prototype.onClose  = function(event) {

    // TODO Validate event and this
    
    // TODO event.data.container -> event.data.self.container
    event.data.container.onRemove(event.data.self);
    
    // NOTE: `this` here will refere to the button element whre the function is binded
    var fieldset = this.parentNode;            // WARNING - DOM method used    
    fieldset.parentNode.removeChild(fieldset); // WARNING - DOM method used 
    
    return undefined;
};

/**
 * @private
 * Change action for the UI-component.
 * Notifies the parent container that the current UI-components state has 
 * changed
 * /attention Since the function will serve as an event handler, `this` will
 * refer to the button element from  where the function is invoked.
 * 
 * @param {jQuery.Event} event Event oject corresponding to the invokation event.
 * By convention `event.data.self` refers to `TransactionUnit` instance
 * 
 * @return undefined
 */     
TransactionUnit.prototype.onChange = function(event) {
    event.data.container.onUpdate(event.data.self);
    
    return undefined;
};

/**
 * @public
 * Validate the current instance.
 * Validates that the input amount is a valid number.
 * 
 * @treturn {Boolean} `true` if the component is valid, `false` otherwise.
 */ 
TransactionUnit.prototype.isValid = function() {
    // TODO use val() method
    var val = this.element.children("input").val();
    return (val.search(/^\d+(\.\d+)?$/) != -1);
};

/**
 * @public
 * Get the current amount value in the input.
 * Gets the amount field value in the current `{user, amount}` UI-component.
 * 
 * @return {Number} The amount value or NaN if the field is invalid.
 */ 
TransactionUnit.prototype.getValue = function() {
    return parseFloat(this.element.children("input").val());
};

/**
 * @public
 * Get the HTML element supported by this JS instance.
 * 
 * @return {jQuery<HTMLFieldSetElement>} The HTML element wrapped in jQuery.
 */ 
TransactionUnit.prototype.getHtmlElement = function() {
    return this.element;
};

// TODO create getUser function
/**
 * @public
 * Get the form data for this instance.
 * Gets the user name value and user amount value from the UI-component for
 * the current instance.
 * 
 * @return Object Data in the following format:
 * {
 *       user {String} User name in UI-component
 *       amount {Number} Amount value in UI-component
 * }
 */ 
TransactionUnit.prototype.getData = function() {
    
    var data = {
        "user": this.element.children("select").val(), 
        "amount": this.getValue()
    };
    
    return data;
};


// TODO @class is currently discouraged
/**
 * @public
 * @class
 * TransactionUnitFactory class.
 * Factory class for producing `TransactionUnit` components.
 */
var TransactionUnitFactory = {

    // TODO change multi line string to concatenated
    // TODO change naming convention to constant
    /**
     * @private
     * @const
     * @type {String}
     * The HTML UI-component markup for `TransactionUnit` components.
     */
    fieldHtml: "<fieldset class=\"gems-transactionUnit\">     \n\
                    <select required></select>        \n\
                    <input type=\"text\" value=\"0\" pattern=\"[0-9]+(\\.[0-9]+)?\"  required/> \n\
                    <button class=\"close\">&times;</button>   \n\
                </fieldset>",
    
    /**
     * @private
     * @type {HTMLFieldSetElement}
     * The HTML UI-component element for `TransactionUnit` components.
     */
    fieldElement: null,
    
    /**
     * @public
     * Initialise the HTML UI-component element withe a given user list
     * 
     * @param {HTMLOptionElement[]} userList Array of user names and 
     * corresponding internal values
     * @return undefined
     */
    initialise: function(userList) {        
        this.fieldElement = jQuery(this.fieldHtml);
        this.fieldElement.children("select").append($(userList).children());
        return undefined;
    },
    
    /**
     * @public
     * Produce a new HTML UI-component and the corresponding JS instance
     * supporting that component.
     * 
     * @param {HTMLFieldSetElement} container The parent HTML element for the
     * produced UI element.
     * @return {TransactionUnit} A new JS instance having the HTML UI-component 
     * and the supporting JS.
     */
    produceUnit: function(container) {
        if(this.fieldElement==null) {
            return null;
        } else {
            return new TransactionUnit(this.fieldElement.clone(), container);
        }
    }
};
