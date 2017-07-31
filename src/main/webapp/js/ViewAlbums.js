function viewAlbums() {
    $("#header").html("Albums");
    var albumsPromise = AlbumAPI.getAll();
    albumsPromise.then(function(result) {
        var name;
        var albumId;
        var photos;
        var coverPhotoId;
        var createDate;
        var container = $("#componentContainer");
        container.empty();
        container.append(buildAddAlbumForm());
        
        for(var i = 0; i < result.length; ++i) {
            var image = $(document.createElement('img'));
            albumId = result[i].albumId;
            name = result[i].name;
            createDate = result[i].createDate;
            photos = result[i].photoList;
            coverPhotoId = photos[0].photoId;
            image.attr("src", "/thumbnail/"+coverPhotoId);
            image.attr("value", albumId);
            image.attr("onclick", "viewAlbum("+albumId+")");
            image.addClass("item");
            container.append(image);
        }
    }, function(err) {
        console.log("There was a problem getting the albums from the server.");
    });
}

function buildAddAlbumForm() {
    var formObject = $(document.createElement('form'));
    formObject.append($(document.createElement('label')).html("Album Name: "));
    
    var nameField = $(document.createElement('input'));
    nameField.attr("id", "name");
    nameField.attr("type", "text");
    formObject.append(nameField);
    formObject.append($(document.createElement('label')).html("Description: "));

    var descriptionField = $(document.createElement('input'));
    descriptionField.attr("id", "description");
    descriptionField.attr("type", "text");
    formObject.append(descriptionField);
    
    var createButton = $(document.createElement('input'));
    createButton.attr("value", "Create Album");
    createButton.attr("type", "button");
    createButton.attr("onClick", "console.log('Create Album')");
    formObject.append(createButton);
    
    return formObject;
}