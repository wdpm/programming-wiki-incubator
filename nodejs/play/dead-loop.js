let arr = []
while (true) {
    arr.push(1);
}

// <--- Last few GCs --->
//
// [22956:000001648B2FD4A0]     1061 ms: Scavenge 765.7 (799.2) -> 765.7 (799.2) MB, 37.6 / 0.0 ms  (average mu = 1.000, current mu = 1.000) allocation failure
// [22956:000001648B2FD4A0]     1502 ms: Scavenge 1148.3 (1181.7) -> 1148.3 (1181.7) MB, 51.7 / 0.0 ms  (average mu = 1.000, current mu = 1.000) allocation failure
// [22956:000001648B2FD4A0]     2168 ms: Scavenge 1722.1 (1755.5) -> 1722.1 (1755.5) MB, 68.9 / 0.0 ms  (average mu = 1.000, current mu = 1.000) allocation failure
//
//
// <--- JS stacktrace --->
//
// FATAL ERROR: invalid array length Allocation failed - JavaScript heap out of memory
// 1: 00007FF62A5E021F napi_wrap+109311
// 2: 00007FF62A585286 v8::internal::OrderedHashTable<v8::internal::OrderedHashSet,1>::NumberOfElementsOffset+33302
// 3: 00007FF62A586056 node::OnFatalError+294
// 4: 00007FF62AE5054E v8::Isolate::ReportExternalAllocationLimitReached+94
// 5: 00007FF62AE353CD v8::SharedArrayBuffer::Externalize+781
// 6: 00007FF62ACDF85C v8::internal::Heap::EphemeronKeyWriteBarrierFromCode+1516
// 7: 00007FF62AD0438F v8::internal::Factory::NewUninitializedFixedArray+111
// 8: 00007FF62ABCA2D0 v8::debug::Script::GetIsolate+8128
// 9: 00007FF62AA54137 v8::internal::interpreter::JumpTableTargetOffsets::iterator::operator=+169671
// 10: 00007FF62AED8EFD v8::internal::SetupIsolateDelegate::SetupHeap+463949
// 11: 0000026A807431B4
