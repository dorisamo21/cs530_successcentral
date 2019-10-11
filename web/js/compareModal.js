$(document).ready(function() {

    $('#compareModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var firstname = button.data('firstname')
        var lastname = button.data('lastname')
        var email = button.data('email')
        var major = button.data('major')
        var year = button.data('year')
        var hobbies = button.data('hobbies')
        var hometown = button.data('hometown')
        var parentEducation = button.data('parenteducation')
        var language = button.data('language')
        var race = button.data('race')
        var gender = button.data('gender')
        var attitudeForDifference = button.data('attitudefordifference')
        var challenge = button.data('challenge')
        var successfulFirstYear = button.data('successfulfirstyear')
        var menteeCount = button.data('menteecount')

        var modal = $(this)

        modal.find('.lastname').text(firstname + ' ' + lastname)
        modal.find('.major').text(major)
        modal.find('.year').text(year)
        modal.find('.hobbies').text(hobbies)
        modal.find('.hometown').text(hometown)
        modal.find('.parentEducation').text(parentEducation)
        modal.find('.language').text(language)
        modal.find('.race').text(race)
        modal.find('.gender').text(gender)
        modal.find('.attitudeForDifference').text(attitudeForDifference)
        modal.find('.challenge').text(challenge)
        modal.find('.successfulFirstYear').text(successfulFirstYear)
        modal.find('.menteeCount').text(menteeCount)
        modal.find('.approveBtn').val(email)
    })
});