# Panduan Build dan Run Aplikasi MyTicket

## 🚀 Cara Menjalankan Aplikasi

### Opsi 1: Melalui Android Studio (DISARANKAN)

1. **Buka Project di Android Studio**
   - Buka Android Studio
   - Pilih "Open" dan pilih folder `MyTicket`
   - Tunggu Gradle sync selesai

2. **Clean Build (Opsional tapi Disarankan)**
   - Klik menu: `Build` → `Clean Project`
   - Tunggu proses selesai
   - Klik menu: `Build` → `Rebuild Project`

3. **Jalankan Aplikasi**
   - Pastikan emulator sudah running atau perangkat fisik terhubung
   - Klik tombol **Run** (▶️) atau tekan `Shift + F10`
   - Aplikasi akan ter-build dan ter-install otomatis

### Opsi 2: Melalui Command Line (Jika Java sudah terinstall)

1. **Set JAVA_HOME** (Windows PowerShell):
   ```powershell
   # Cek lokasi Java (biasanya di Android Studio)
   $env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
   # atau
   $env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
   ```

2. **Build Aplikasi**:
   ```powershell
   .\gradlew.bat clean
   .\gradlew.bat assembleDebug
   ```

3. **Install ke Perangkat**:
   ```powershell
   .\gradlew.bat installDebug
   ```

### Opsi 3: Menggunakan Android Studio Terminal

1. Buka Android Studio
2. Buka Terminal di Android Studio (View → Tool Windows → Terminal)
3. Jalankan:
   ```bash
   ./gradlew clean
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

## 📱 Fitur Aplikasi

### Home Screen
- ✅ List semua tiket
- ✅ Filter: All, Open, In Progress, Done
- ✅ Floating Action Button untuk membuat tiket baru
- ✅ Update status langsung dari list item

### New Ticket Screen
- ✅ Form input dengan validasi
- ✅ Field: Title, Description, Category
- ✅ Status default: OPEN

### Detail Screen
- ✅ Menampilkan informasi lengkap tiket
- ✅ Status, Title, Description, Category
- ✅ Timestamps (Created & Updated)

## 🧪 Unit Test

Unit test sudah diperbaiki dan menggunakan package yang benar:
- `com.azwar.myticket.ExampleUnitTest`
- `com.azwar.myticket.ExampleInstrumentedTest`

## ⚠️ Troubleshooting

### Jika Build Gagal:
1. **Clean Project**: `Build` → `Clean Project`
2. **Invalidate Caches**: `File` → `Invalidate Caches...` → `Invalidate and Restart`
3. **Sync Gradle**: Klik tombol "Sync Project with Gradle Files"

### Jika Ada Error Hilt:
- Pastikan `@HiltAndroidApp` ada di `MyTicketApp.kt`
- Pastikan `@AndroidEntryPoint` ada di `MainActivity.kt`

### Jika Database Error:
- Uninstall aplikasi dari perangkat/emulator
- Rebuild dan install ulang

## 📝 Catatan

- Database Room disimpan lokal di perangkat
- Data akan hilang jika aplikasi di-uninstall
- Semua fitur CRUD sudah diimplementasikan dengan use cases

