def _setup_db():
    from .models import DBSession, Base
    from sqlalchemy import create_engine
    engine = create_engine("sqlite:///")
    DBSession.remove()
    DBSession.configure(bind=engine)
    Base.metadata.create_all(bind=engine)
    return DBSession

def _teardown_db():
    from .models import DBSession, Base

    DBSession.rollback()
    Base.metadata.drop_all(bind=DBSession.bind)
    DBSession.remove()
