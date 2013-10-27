package com.gtunes

import org.springframework.dao.DataIntegrityViolationException

class ArtistController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [artistInstanceList: Artist.list(params), artistInstanceTotal: Artist.count()]
    }

    def create() {
        [artistInstance: new Artist(params)]
    }

    def save() {
        def artistInstance = new Artist(params)
        if (!artistInstance.save(flush: true)) {
            render(view: "create", model: [artistInstance: artistInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'artist.label', default: 'Artist'), artistInstance.id])
        redirect(action: "show", id: artistInstance.id)
    }

    def show(Long id) {
        def artistInstance = Artist.get(id)
        if (!artistInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'artist.label', default: 'Artist'), id])
            redirect(action: "list")
            return
        }

        [artistInstance: artistInstance]
    }

    def edit(Long id) {
        def artistInstance = Artist.get(id)
        if (!artistInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'artist.label', default: 'Artist'), id])
            redirect(action: "list")
            return
        }

        [artistInstance: artistInstance]
    }

    def update(Long id, Long version) {
        def artistInstance = Artist.get(id)
        if (!artistInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'artist.label', default: 'Artist'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (artistInstance.version > version) {
                artistInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'artist.label', default: 'Artist')] as Object[],
                          "Another user has updated this Artist while you were editing")
                render(view: "edit", model: [artistInstance: artistInstance])
                return
            }
        }

        artistInstance.properties = params

        if (!artistInstance.save(flush: true)) {
            render(view: "edit", model: [artistInstance: artistInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'artist.label', default: 'Artist'), artistInstance.id])
        redirect(action: "show", id: artistInstance.id)
    }

    def delete(Long id) {
        def artistInstance = Artist.get(id)
        if (!artistInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'artist.label', default: 'Artist'), id])
            redirect(action: "list")
            return
        }

        try {
            artistInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'artist.label', default: 'Artist'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'artist.label', default: 'Artist'), id])
            redirect(action: "show", id: id)
        }
    }
}
