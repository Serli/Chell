/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
if (typeof(chell) == 'undefined') {
  chell = {};
}

if (typeof(chell.button) == 'undefined') {
  function ChellButton() {
  }

  ChellButton.prototype.submitAction = function(elmt, actionUrl) {
    elmt.form.action = actionUrl;
    elmt.form.submit();
  };

  ChellButton.prototype.cancelUpload = function(form) {
    var elmts = form.getElementsByTagName('input');
    for (var i = 0; i < elmts.length; i++) {
        var elmt = elmts[i];
        if (elmt.getAttribute('type') == 'file') {
            elmt.removeAttribute('name');
        }
    }
  };

  chell.button = new ChellButton();
}
