function selectImages(albumId) {
    console.log("Getting album photo information.");
    var photoPromise = PhotoAPI.getPhotosWithoutAlbum(0);
    photoPromise.then(function(result) {
        $("#header").html("Add Images to Album");
        var container = $("#componentContainer");
        container.empty();
        var imagePicker = buildImagePickerSelect();
        container.append(imagePicker);
        var photoId;
        var image;
        for(var i = 0; i < result.length; ++i) {
            photoId = result[i].photoId;

            var imageOption = $(document.createElement('option'));
            imageOption.attr("data-img-src", "/thumbnail/"+photoId);
            imageOption.attr("value", photoId);
            imageOption.addClass("item");
            imagePicker.append(imageOption);
        }
        $("#imagePickerContainer").imagepicker();
    }, function(err) {
        console.log("There was a problem fetching images without an album.");
        console.log(err);
    });
}

function buildImagePickerSelect() {
    var imagePicker = $(document.createElement('select'));
    imagePicker.addClass("image-picker");
    imagePicker.attr("multiple", "multiple");
    imagePicker.attr("id", "imagePickerContainer");
    return imagePicker;
}