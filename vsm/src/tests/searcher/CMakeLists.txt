# Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
vespa_add_executable(vsm_searcher_test_app TEST
    SOURCES
    searcher.cpp
    DEPENDS
    vsm
)
vespa_add_test(NAME vsm_searcher_test_app COMMAND vsm_searcher_test_app)
