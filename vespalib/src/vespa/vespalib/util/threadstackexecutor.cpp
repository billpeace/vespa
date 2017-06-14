// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.

#include "threadstackexecutor.h"

namespace vespalib {

VESPA_THREAD_STACK_TAG(unnamed_nonblocking_executor);

bool
ThreadStackExecutor::acceptNewTask(MonitorGuard &)
{
    return isRoomForNewTask();
}

void
ThreadStackExecutor::wakeup(MonitorGuard &)
{
}

ThreadStackExecutor::ThreadStackExecutor(uint32_t threads, uint32_t stackSize,
                                         uint32_t taskLimit)
    : ThreadStackExecutorBase(stackSize, taskLimit, unnamed_nonblocking_executor)
{
    start(threads);
}

ThreadStackExecutor::ThreadStackExecutor(uint32_t threads, uint32_t stackSize,
                                         init_fun_t init_function, uint32_t taskLimit)
    : ThreadStackExecutorBase(stackSize, taskLimit, std::move(init_function))
{
    start(threads);
}

ThreadStackExecutor::~ThreadStackExecutor()
{
    cleanup();
}

} // namespace vespalib
