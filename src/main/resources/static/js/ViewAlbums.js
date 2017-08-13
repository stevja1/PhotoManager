var albumPage = 0;
/**
 * <div id="componentContainer">
 *   <div id="galleryContainer">
 *     <div id="albumIcon">
 *       <img id="image" />
 *       <div id="albumTitle"></div>
 *     </div>
 *     <div id="albumIcon">
 *       <img id="image" />
 *       <div id="albumTitle"></div>
 *     </div>
 *     ...
 *   </div>
 *   <div id="createAlbumForm"></div>
 * </div>
 */
function viewAlbums() {
    view = "view_albums";
    $("#header").html("Albums");
    var albumsPromise = AlbumAPI.getAll(albumPage);
    albumsPromise.then(function(result) {
        var name;
        var albumId;
        var photos;
        var coverPhotoId;
        var createDate;
        var container = $("#componentContainer");
        container.empty();
        container.append(buildAddAlbumForm());
        
        var galleryContainer = $(document.createElement("div"));
        galleryContainer.attr("id", "galleryContainer");

        for(var i = 0; i < result.length; ++i) {
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
            image.addClass("item");

            var albumTitle = $(document.createElement('div'));
            albumTitle.html(name);
            albumTitle.addClass("albumIconLabel");

            var albumIcon = $(document.createElement('div'));
            albumIcon.addClass("albumIcon");
            albumIcon.attr("onclick", "viewAlbum("+albumId+")");
            albumIcon.append(image);
            albumIcon.append(albumTitle);
            galleryContainer.append(albumIcon);
        }
        container.append(galleryContainer);
    }, function(err) {
        console.log("There was a problem getting the albums from the server.");
    });
}

function buildAlbumCover(albumId, imageURL, imageTitle) {
    var image = $(document.createElement('img'));
    image.attr("src", imageURL);
    image.attr("value", albumId);
    image.addClass("item");
    
    var albumTitle = $(document.createElement('div'));
    albumTitle.html(imageTitle);
    albumTitle.addClass("albumIconLabel");

    var albumIcon = $(document.createElement('div'));
    albumIcon.addClass("albumIcon");
    albumIcon.attr("onclick", "viewAlbum("+albumId+")");
    albumIcon.append(image);
    albumIcon.append(albumTitle);

    return albumIcon;
}

function buildAddAlbumForm() {
    var formObject = $(document.createElement('form'));
    
    formObject.append($(document.createElement("p")).html("Create An Album"));
    
    formObject.append($(document.createElement('label')).html("Album Name: "));
    var nameField = $(document.createElement('input'));
    nameField.attr("id", "name");
    nameField.attr("type", "text");
    formObject.append(nameField);
    formObject.append(document.createElement("br"));
    
    formObject.append($(document.createElement('label')).html("Description: "));
    var descriptionField = $(document.createElement('input'));
    descriptionField.attr("id", "description");
    descriptionField.attr("type", "text");
    formObject.append(descriptionField);
    formObject.append(document.createElement("br"));
    
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

$(window).scroll(function() {
    if(view != "view_albums") {
        return;
    }
    if($(window).scrollTop() + $(window).height() == $(document).height()
      && $(window).height() != $(document).height()) {
        ++albumPage;
        
        var galleryContainer = $("#galleryContainer");
        var albumPromise = AlbumAPI.getAll(albumPage);
        albumPromise.then(function(result) {
            for(var i = 0; i < result.length; ++i) {
                photoId = result[i].photoId;
                var imageOption = $(document.createElement('option'));
                imageOption.attr("data-img-src", "/thumbnail/"+photoId);
                imageOption.attr("value", photoId);
                imageOption.addClass("item");
                imagePicker.append(imageOption);
            }
        }, function(err) {
            console.log("There was a problem loading additional albums.");
        });
    }
});