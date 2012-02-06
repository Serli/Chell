/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
if (typeof(chell) == 'undefined') {
  chell = {};
}

if (typeof(chell.upload) == 'undefined') {
  function ChellUpload() {
  }

  ChellUpload.prototype.showInput = function(containerClass, fieldName, elmt) {
    while (elmt.className != containerClass) {
      elmt = elmt.parentNode;
    }
    var parent = elmt.parentNode;
    var input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('name', fieldName);
    parent.removeChild(elmt);
    parent.appendChild(input);
  };
  
  chell.upload = new ChellUpload();
}
