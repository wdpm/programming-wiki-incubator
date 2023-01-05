import logging
logger = logging.getLogger(__name__)  # __name__处代入模块路径‘polls.views’

def detail(request, poll_id):
    poll = get_object_or_404(Poll, id=poll_id)
    logger.info('Poll: %s', poll.id)
    return TemplateResponse(request, 'polls/detail.html',
                            {'poll': poll})
