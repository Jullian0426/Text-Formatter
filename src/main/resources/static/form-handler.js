// form-handler.js
document.addEventListener('DOMContentLoaded', function () {
    // Remove the file input when the remove button is clicked
    document.getElementById("removeFileBtn").addEventListener("click", function () {
        document.getElementById("fileUpload").value = "";
        document.getElementById('textInput').disabled = false;
    });

    // Disable the text input if a file is selected
    document.getElementById('fileUpload').addEventListener('change', function() {
        if (this.files.length > 0) {
            document.getElementById('textInput').disabled = true;
        } else {
            document.getElementById('textInput').disabled = false;
        }
    });

    // Handle submit
    document.getElementById("textFormatterForm").onsubmit = function() {
        var textInput = document.getElementById("textInput").value;
        var fileInput = document.getElementById("fileUpload").files.length;

        if (textInput.trim() === "" && fileInput === 0) {
            alert("Please enter text or select a file.");
            return false;
        }

        return true;
    };
});
