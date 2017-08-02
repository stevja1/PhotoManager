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
            var albumIcon = $(document.createElement('div'));
            var image = $(document.createElement('img'));
            albumId = result[i].albumId;
            name = result[i].name;
            createDate = result[i].createDate;
            photos = result[i].photoList;
            if(photos.length > 0) {
                coverPhotoId = photos[0].photoId;
                image.attr("src", "/thumbnail/"+coverPhotoId);
            } else {
                image.attr("src", "/images/emptyAlbum.png");
            }
            image.attr("value", albumId);
//            image.attr("onclick", "viewAlbum("+albumId+")");
            image.addClass("item");
            albumIcon.append(image);
            var albumTitle = $(document.createElement('p'));
            albumTitle.html(name);
            albumIcon.attr("onclick", "viewAlbum("+albumId+")");
            albumIcon.append(albumTitle);
            container.append(albumIcon);
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
    createButton.attr("onClick", "saveAlbum()");
    formObject.append(createButton);
    
    return formObject;
}

function saveAlbum() {
    var name = $("#name").val();
    var description = $("#description").val();
    var promise = AlbumAPI.save(-1, name, description);
    promise.then(function(result) {
        viewAlbums();
    }, function(err) {
        console.log("There was a problem saving the new album.");
        console.log(err);
    });
}