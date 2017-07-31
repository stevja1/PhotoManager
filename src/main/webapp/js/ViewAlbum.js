function viewAlbum(albumId) {
    console.log("Getting album photo information.");
    var albumPromise = AlbumAPI.get(albumId);
    
    albumPromise.then(function(result) {
        console.log("Retrieved "+result.photoList.length+" records");
        
        $("#header").html("Album: "+result.name);
        var container = $("#componentContainer");
        container.empty();
        
        var photoId;
        var image;
        for(var i = 0; i < result.photoList.length; ++i) {
            photoId = result.photoList[i].photoId;

            var image = $(document.createElement('img'));
            image.attr("src", "/thumbnail/"+photoId);
            container.append(image);
        }
        var addPhotosToAlbumButton = $(document.createElement('button'));
        var buttonImage = $(document.createElement('img'));
        buttonImage.attr("src", "/images/addPhotosToAlbum.png");
        addPhotosToAlbumButton.append(buttonImage);
        addPhotosToAlbumButton.attr("onClick", "selectImages("+albumId+")");
        container.append(addPhotosToAlbumButton);        
    }, function(err) {
        console.log("There was a problem loading album "+albumId);
        console.log(err);
    });
}
